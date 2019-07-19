package com.example.minymart.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.adapter.CategoriesAdapter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.connection.ConnectivityReceiver;
import com.example.minymart.model.Category;
import com.example.minymart.model.respons.ResponsCategories;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends BaseActivity implements CategoriesAdapter.OnItemClickListener, ConnectivityReceiver.ConnectivityReceiverListener{

    //Component
    @BindView(R.id.recyclerview_category)
    RecyclerView mRecyclerviewCategory;
    @BindView(R.id.constraint)
    ConstraintLayout mCl;

    List<Category> mCategory;
    BaseApiService mApiService;
    CategoriesAdapter mAdapter;
    private GridLayoutManager lLayout;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        attachView(this);
        initView();
        checkConnection();


    }

    public void initView(){

        mApiService = UtilsApi.getApiService();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(CategoryActivity.this, 3);
        mRecyclerviewCategory.setLayoutManager(mGridLayoutManager);
        mRecyclerviewCategory.setHasFixedSize(true);
        mRecyclerviewCategory.setItemAnimator(new DefaultItemAnimator());
        mCategory = new ArrayList<>();
        mAdapter = new CategoriesAdapter(this, mCategory);
        mAdapter.setOnItemClickListener(this);
        mRecyclerviewCategory.setAdapter(mAdapter);
        if (isOnline()){
            loadCategories();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_shopping){
            startActivity(new Intent(CategoryActivity.this, CartActivity.class));
        }

        return true;

    }

    public void loadCategories(){
        showLoading();
        Call<ResponsCategories> categoriesCall = mApiService.getCategories();
        categoriesCall.enqueue(new Callback<ResponsCategories>() {
            @Override
            public void onResponse(Call<ResponsCategories> call, Response<ResponsCategories> response) {
                Log.d("category", "onResponse" + response.body().getCategorie().size());
                mCategory.clear();
                mCategory.addAll(response.body().getCategorie());
                mAdapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponsCategories> call, Throwable t) {

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

    @Override
    public void onItemClick(int position) {
        Category category = mCategory.get(position);
        String[] categoryData={category.getId()};
        openDetailActivity(categoryData);
    }

    private void openDetailActivity(String[] data) {
        Intent intent = new Intent(CategoryActivity.this, ProductsActivity.class);
        intent.putExtra("ID",data[0]);
        startActivity(intent);
    }

    @Override
    public void onShowItemClick(int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
