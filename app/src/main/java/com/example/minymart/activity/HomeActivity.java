package com.example.minymart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.adapter.CategoriesAdapter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.connection.ConnectivityReceiver;
import com.example.minymart.fragment.BannerFragment;
import com.example.minymart.model.Category;
import com.example.minymart.model.respons.ResponsCategories;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements CategoriesAdapter.OnItemClickListener, ConnectivityReceiver.ConnectivityReceiverListener{

    //Component
    @BindView(R.id.recycler_home_category)
    RecyclerView mRecyclerviewCategory;
    @BindView(R.id.viewAll)
    TextView mViewAll;
    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.constraint)
    ConstraintLayout mCl;

    CategoriesAdapter mAdapter;
    BaseApiService mApiService;
    Context mContext;
    List<Category> mCategory;
    LinearLayoutManager layoutManager;

    //Fragment
    private BannerFragment bannerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        attachView(this);
        initView();
        checkConnection();
        transactionFragment();

    }

    private void transactionFragment() {
        bannerFragment = new BannerFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_banner, bannerFragment)
                .commit();
    }

    public void initView(){

        mApiService = UtilsApi.getApiService();
        mRecyclerviewCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerviewCategory.setLayoutManager(layoutManager);
        mRecyclerviewCategory.setItemAnimator(new DefaultItemAnimator());
        mCategory = new ArrayList<>();
        mAdapter = new CategoriesAdapter(this, mCategory);
        mAdapter.setOnItemClickListener(this);
        mRecyclerviewCategory.setAdapter(mAdapter);
        if (isOnline()){
            loadCategories();
        }
        else {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_shopping) {
            toCart();
        }
        if (item.getItemId() == R.id.menu_user){
            toProfile();
        }

        return true;
    }

    public void loadCategories(){
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponsCategories> categoriesCall = mApiService.getCategories();
        categoriesCall.enqueue(new Callback<ResponsCategories>() {
            @Override
            public void onResponse(Call<ResponsCategories> call, Response<ResponsCategories> response) {
                Log.d("category", "onResponse" + response.body().getCategorie().size());
                mCategory.clear();
                mCategory.addAll(response.body().getCategorie());
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponsCategories> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected(this);
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        if (!isConnected) {
            Snackbar snackbar = Snackbar

                    .make(mCl, "Maaf! Tidak terhubung ke internet", Snackbar.LENGTH_INDEFINITE)

                    .setAction("Coba lagi", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            recreate();
                        }

                    });

            snackbar.show();
        }
    }


    public void onClickView(View view){
        switch (view.getId()){
            case R.id.viewAll :
                viewAll();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Category category = mCategory.get(position);
        String[] categoryData = {category.getId()};
        openDetailActivity(categoryData);

    }

    private void openDetailActivity(String[] data) {
        Intent intent = new Intent(HomeActivity.this, ProductsActivity.class);
        intent.putExtra("ID",data[0]);
        startActivity(intent);
    }

    @Override
    public void onShowItemClick(int position) {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
