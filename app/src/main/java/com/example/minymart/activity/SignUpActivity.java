package com.example.minymart.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {

    //Component
    @BindView(R.id.etx_first_name)
    EditText mFirstName;
    @BindView(R.id.etx_last_name)
    EditText mLastName;
    @BindView(R.id.etx_email)
    EditText mEmail;
    @BindView(R.id.etx_password)
    EditText mPassword;
    @BindView(R.id.etx_birthdate)
    EditText mBirthday;
    @BindView(R.id.etx_gender)
    EditText mGender;
    @BindView(R.id.etx_height)
    EditText mHeight;
    @BindView(R.id.etx_weight)
    EditText mWeight;
    @BindView(R.id.btn_signup)
    Button mSignUp;

    BaseApiService mApiService;
    SweetAlertDialog loading;
    Context mContext;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        attachView(this);
        initView();

    }

    private void initView() {

        mApiService = UtilsApi.getApiService();
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

    }

    public void requestRegister(){
        showLoading();
        String fName = mFirstName.getText().toString();
        String lName = mLastName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String birthdate = mBirthday.getText().toString();
        String gender = mGender.getText().toString();
        String height = mHeight.getText().toString();
        String weight = mWeight.getText().toString();

        if (TextUtils.isEmpty(fName) && TextUtils.isEmpty(lName) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(birthdate) &&
                TextUtils.isEmpty(gender) && TextUtils.isEmpty(height) && TextUtils.isEmpty(weight)){

            alertDialogEmpty();
            hideLoading();
        }
        else {
            mApiService.createUser(fName, lName, email, password, birthdate, gender, height, weight).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.i("debug", "onResponse: BERHASIL");
                        hideLoading();

                        try {
                            JSONObject jsonRESULTS = new JSONObject(response.body().string());
                            if (jsonRESULTS.getJSONObject("meta").getString("message").equals("Success")){
                                String token = jsonRESULTS.getString("access_token");
                                Log.d("respons", "message" + token);
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                sharedPrefManager.getToken(SharedPrefManager.TOKEN, token);
                                Toast.makeText(SignUpActivity.this, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            }
                            else {
                                String error_message = jsonRESULTS.getString("error_msg");
                                Toast.makeText(SignUpActivity.this, error_message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    else {
                        Log.i("debug", "onResponse: GA BERHASIL");
                        hideLoading();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onClickView(View view) {
        switch (view.getId()){
            case R.id.txt_signup :
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
            case R.id.btn_signup :
                showLoading();
                requestRegister();
                break;
        }
    }
}
