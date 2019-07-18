package com.example.minymart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.adapter.ProductAdapter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.model.Products;
import com.example.minymart.model.respons.ResponsProduct;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends BaseActivity implements ProductAdapter.OnItemClickListener {

    @BindView(R.id.recyclerview_product)
    RecyclerView mRecyclerView;
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
        showLoading();
        loadProduct();

    }

    public void initView(){
        mApiService = UtilsApi.getApiService();
        Intent i=this.getIntent();
        id = i.getExtras().getString("ID");
        Toast.makeText(ProductsActivity.this, "" + id, Toast.LENGTH_SHORT).show();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(ProductsActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProduct = new ArrayList<>();
        mAdapter = new ProductAdapter(this, mProduct);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void loadProduct(){
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
}
