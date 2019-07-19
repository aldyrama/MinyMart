package com.example.minymart.Base;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.view.View;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseFragment extends Fragment {

    public Fragment fragment;
    SweetAlertDialog loading;

    public void attachView(View view) {

        ButterKnife.bind(view);

    }

    public void showLoading(){
        loading = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        loading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loading.setTitleText("Mohon tunggu");
        loading.setCancelable(false);
        loading.show();
    }

    public boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();

    }

}
