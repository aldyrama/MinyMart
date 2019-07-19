package com.example.minymart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.adapter.CartAdapter;
import com.example.minymart.adapter.ConfirmationAdapter;
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

public class ConfirmationActivity extends BaseActivity {
    @BindView(R.id.pay_recyclerview)
    RecyclerView mRecyclerView;
    List<Cart> mCart;
    BaseApiService mApiService;
    LinearLayoutManager layoutManager;
    ConfirmationAdapter mAdapter;
    String token;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        attachView(this);
        initView();
        showLoading();
        loadPay();

    }

    @SuppressLint("WrongConstant")
    private void initView() {
        mApiService = UtilsApi.getApiService();
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCart = new ArrayList<>();
        mAdapter = new ConfirmationAdapter(this, mCart);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void loadPay() {
        Call<ResponsCart> call = mApiService.getPay(token);
        call.enqueue(new Callback<ResponsCart>() {
            @Override
            public void onResponse(Call<ResponsCart> call, Response<ResponsCart> response) {
                mCart.clear();
                mCart.addAll(response.body().getCarts().getProducts());
                mAdapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponsCart> call, Throwable t) {

            }
        });
    }

    public void onClickView(View view){
        switch (view.getId()){
            case R.id.btn_pay :
                toSucces();
                break;
        }
    }
}
