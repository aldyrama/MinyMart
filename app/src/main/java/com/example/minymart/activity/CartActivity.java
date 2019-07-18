package com.example.minymart.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.adapter.CartAdapter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.model.Cart;
import com.example.minymart.model.respons.ResponsCart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity {

    @BindView(R.id.recyclerview_cart)
    RecyclerView mRecyclerView;
    List<Cart> mCart;
    BaseApiService mApiService;
    LinearLayoutManager layoutManager;
    CartAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        attachView(this);
        showLoading();
        initView();
        loadCart();
    }

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
        Call<ResponsCart> cartCall = mApiService.getCart("eyJ0eXAi...");
        cartCall.enqueue(new Callback<ResponsCart>() {

            @Override
            public void onResponse(Call<ResponsCart> call, Response<ResponsCart> response) {
                Log.d("cart", "onResponse" + response.body().getCarts().size());

            }

            @Override
            public void onFailure(Call<ResponsCart> call, Throwable t) {
                Toast.makeText(CartActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
