package com.example.minymart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class SuccsessActivity extends BaseActivity {

    @BindView(R.id.txt_no_transaksi)
    TextView mTransaksi;
    List<Cart> mCart;
    BaseApiService mApiService;
    String token;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succsess);

        attachView(this);
        showLoading();
        initView();
        loadCart();
    }

    private void loadCart() {
        Call<ResponsCart> cartCall = mApiService.getCart(token);
        cartCall.enqueue(new Callback<ResponsCart>() {

            @Override
            public void onResponse(Call<ResponsCart> call, Response<ResponsCart> response) {
                Log.d("cartdata", "onResponse" + response.body().getCarts().getProducts().size());
                String txId = response.body().getCarts().getTx_id();
                mTransaksi.setText(txId);
                hideLoading();

            }

            @Override
            public void onFailure(Call<ResponsCart> call, Throwable t) {
                Log.d("error", "message" + t.getMessage());
//                Toast.makeText(CartActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initView() {
        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);
        token = sharedPrefManager.setToken();

    }


    public void onClickView(View view){
        switch (view.getId()){
            case R.id.btn_prev :
                toHome();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toHome();
    }
}
