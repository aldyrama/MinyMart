package com.example.minymart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.minymart.connection.ConnectivityReceiver;
import com.example.minymart.model.Cart;
import com.example.minymart.model.respons.ResponsCart;
import com.example.minymart.utils.SharedPrefManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationActivity extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    @BindView(R.id.pay_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.constraint)
    ConstraintLayout mCl;

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
        checkConnection();

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
        if (isOnline()){
            loadPay();
        }
    }

    public void loadPay() {
        showLoading();
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
            case R.id.btn_pay :
                toSucces();
                break;
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
