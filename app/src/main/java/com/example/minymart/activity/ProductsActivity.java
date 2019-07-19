package com.example.minymart.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.adapter.ProductAdapter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.connection.ConnectivityReceiver;
import com.example.minymart.model.Products;
import com.example.minymart.model.respons.ResponsProduct;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends BaseActivity implements ProductAdapter.OnItemClickListener, ConnectivityReceiver.ConnectivityReceiverListener{

    @BindView(R.id.recyclerview_product)
    RecyclerView mRecyclerView;
    @BindView(R.id.constraint)
    ConstraintLayout mCl;

    BaseApiService mApiService;
    String id;
    LinearLayoutManager layoutManager;
    List<Products> mProduct;
    ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        attachView(this);
        initView();
        checkConnection();

    }

    public void initView(){
        mApiService = UtilsApi.getApiService();
        Intent i=this.getIntent();
        id = i.getExtras().getString("ID");
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(ProductsActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProduct = new ArrayList<>();
        mAdapter = new ProductAdapter(this, mProduct);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        if (isOnline()){
            loadProduct();
        }
    }

    public void loadProduct(){
        showLoading();
        Call<ResponsProduct> responsProductCall = mApiService.getProductResult(id, String.valueOf(1));
        responsProductCall.enqueue(new Callback<ResponsProduct>() {
            @Override
            public void onResponse(Call<ResponsProduct> call, Response<ResponsProduct> response) {
                Log.d("product", "onResponse" + response.body().getProducts().size());
                mProduct.clear();
                mProduct.addAll(response.body().getProducts());
                mAdapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponsProduct> call, Throwable t) {

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
        Products products = mProduct.get(position);
        String[] productData = {String.valueOf(products.getId()), products.getName(), products.getImage(), products.getDescription()};
        openDetailActivity(productData);
    }

    private void openDetailActivity(String[] data) {
        Intent intent = new Intent(ProductsActivity.this, DetailProductActivity.class);
        intent.putExtra("ID",data[0]);
        intent.putExtra("NAME", data[1]);
        intent.putExtra("IMAGE", data[2]);
        intent.putExtra("DESCRIPTION", data[3]);
        startActivity(intent);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
