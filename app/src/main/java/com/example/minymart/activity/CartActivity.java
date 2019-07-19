package com.example.minymart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.adapter.CartAdapter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.model.Cart;
import com.example.minymart.model.respons.ResponsCart;
import com.example.minymart.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity {

    @BindView(R.id.recyclerview_cart)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_total)
    TextView mTotal;
    List<Cart> mCart;
    BaseApiService mApiService;
    LinearLayoutManager layoutManager;
    CartAdapter mAdapter;
    String token;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        attachView(this);
        sharedPrefManager = new SharedPrefManager(this);
        token = sharedPrefManager.setToken();
        showLoading();
        initView();
        loadCart();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        mApiService = UtilsApi.getApiService();
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCart = new ArrayList<>();
        mAdapter = new CartAdapter(this, mCart);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void loadCart() {
        Call<ResponsCart> cartCall = mApiService.getCart(token);
        cartCall.enqueue(new Callback<ResponsCart>() {

            @Override
            public void onResponse(Call<ResponsCart> call, Response<ResponsCart> response) {
                Log.d("cartdata", "onResponse" + response.body().getCarts().getProducts().size());
                mCart.clear();
                mCart.addAll(response.body().getCarts().getProducts());
                mAdapter.notifyDataSetChanged();
                int total = response.body().getCarts().getTotal_price();
                mTotal.setText(String.valueOf(total));
                hideLoading();

            }

            @Override
            public void onFailure(Call<ResponsCart> call, Throwable t) {
                Log.d("error", "message" + t.getMessage());
//                Toast.makeText(CartActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onClickView(View view){
        switch (view.getId()){
            case R.id.btn_checkout :
                startActivity(new Intent(CartActivity.this, ConfirmationActivity.class));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
