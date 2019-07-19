package com.example.minymart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minymart.Base.BaseActivity;
import com.example.minymart.R;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.model.respons.ResponsUser;
import com.example.minymart.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {

    SharedPrefManager sharedPrefManager;
    BaseApiService mApiService;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPrefManager = new SharedPrefManager(this);

        token = sharedPrefManager.setToken();
        initView();
        loadProfile();
    }

    public void initView(){
        mApiService = UtilsApi.getApiService();


    }

    public void loadProfile(){
        Call<ResponseBody> call = mApiService.getProfile(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("user", "onResponse" + response.body().string());
                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                    String fName = jsonRESULTS.getJSONObject("profile_data").getString("first_name");
                    Log.d("nama", "onResponse" + fName);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
//        Call<ResponsUser> call = mApiService.getProfile(token);
//        call.enqueue(new Callback<ResponsUser>() {
//            @Override
//            public void onResponse(Call<ResponsUser> call, Response<ResponsUser> response) {
////                Log.d("user", "onResponse" + response.body().getUsers().size());
//                Toast.makeText(ProfileActivity.this, "success", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponsUser> call, Throwable t) {
//                Log.d("error", "message" + t.getMessage());
//                Toast.makeText(ProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

    }

    public void onClickView(View view){
        switch (view.getId()){
            case R.id.button :
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                break;
        }
    }
}
