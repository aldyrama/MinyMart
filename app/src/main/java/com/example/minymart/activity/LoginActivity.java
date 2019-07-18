package com.example.minymart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.connection.ConnectivityReceiver;
import com.example.minymart.model.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    //Component
    @BindView(R.id.img_help)
    ImageView mHelp;
    @BindView(R.id.etx_email)
    EditText mEmail;
    @BindView(R.id.etx_password)
    EditText mPassword;
    @BindView(R.id.constraint)
    ConstraintLayout mCl;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        attachView(this);
        initView();
        checkConnection();

    }

    private void initView() {

        mApiService = UtilsApi.getApiService();

    }

    public void requestLogin(){
        showLoading();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email)){

            alertDialogEmpty();
            hideLoading();

        }

        else if (TextUtils.isEmpty(password)){
            alertDialogEmpty();
            hideLoading();
        }

        else {

            mApiService.requestLogin(email, password).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        hideLoading();
                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            Log.d("respons", "message" + jsonRESULTS);
                            if (jsonRESULTS.getJSONObject("meta").getString("message").equals("Success")){
                                String token = jsonRESULTS.getString("access_token");
                                Log.d("respons", "message" + token);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                Toast.makeText(LoginActivity.this, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                                Toast.makeText(LoginActivity.this, "" + token, Toast.LENGTH_SHORT).show();

                            }
                            else {
                                String error_message = jsonRESULTS.getString("error_msg");
                                Toast.makeText(LoginActivity.this, error_message, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        hideLoading();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
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
            case R.id.img_help :
                startActivity(new Intent(LoginActivity.this, HelpActivity.class));
                break;
            case R.id.btn_signin :
                checkConnection();
                if (isOnline()){
                    requestLogin();
                }
                break;
            case R.id.txt_signup :
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
