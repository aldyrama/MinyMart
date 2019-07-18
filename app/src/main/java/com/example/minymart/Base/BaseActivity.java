package com.example.minymart.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;

import com.example.minymart.activity.CartActivity;
import com.example.minymart.activity.CategoryActivity;
import com.example.minymart.activity.HomeActivity;
import com.example.minymart.activity.LoginActivity;
import com.example.minymart.activity.ProfileActivity;
import com.example.minymart.activity.SignUpActivity;
import com.example.minymart.model.SharedPrefManager;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity extends AppCompatActivity {

    public Activity activity;
    SweetAlertDialog loading;
    SharedPrefManager sharedPrefManager;

    public void attachView(Activity activity) {

        ButterKnife.bind(activity);

    }

    public void showLoading(){
        loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loading.setTitleText("Mohon tunggu");
        loading.setCancelable(false);
        loading.show();
    }

    public void hideLoading(){
        loading.dismiss();
    }

    public void alertDialogEmpty(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Opps...")
                .setContentText("Mohon Isikan Data Anda Dengan Lengkap")
                .show();
    }

    public boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();

    }

    //Inten
    public void toLogin(){
        startActivity(new Intent(BaseActivity.this, LoginActivity.class));
    }
    public void toSignUp(){
        startActivity(new Intent(BaseActivity.this, SignUpActivity.class));
    }
    public void toHome(){
        startActivity(new Intent(BaseActivity.this, HomeActivity.class));
    }
    public void toProfile(){
        startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
    }
    public void toCart(){
        startActivity(new Intent(BaseActivity.this, CartActivity.class));
    }
    public void logOut(){
        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
        startActivity(new Intent(BaseActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
    public void viewAll(){
        startActivity(new Intent(BaseActivity.this, CategoryActivity.class));
    }
}
