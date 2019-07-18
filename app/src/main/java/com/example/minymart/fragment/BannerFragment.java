package com.example.minymart.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minymart.R;
import com.example.minymart.adapter.PicassoImageLoadingService;
import com.example.minymart.adapter.SliderAdpter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.model.Banner;
import com.example.minymart.model.respons.ResponsBanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;

public class BannerFragment extends Fragment {

    private Slider slider;
    BaseApiService mApiService;
    List<Banner> mBanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner, container, false);
        Slider.init(new PicassoImageLoadingService(getContext()));

        mApiService = UtilsApi.getApiService();
        slider = view.findViewById(R.id.banner_slider);
        loadBanner();
        slider.postDelayed(new Runnable() {
            @Override
            public void run() {
                slider.setAdapter(new SliderAdpter());
                slider.setSelectedSlide(0);
            }
        }, 1500);

        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;

    }

    public void loadBanner(){
        Call<ResponsBanner> call = mApiService.getBanner();
        call.enqueue(new Callback<ResponsBanner>() {
            @Override
            public void onResponse(Call<ResponsBanner> call, Response<ResponsBanner> response) {
                Log.d("banner", "respons " + response.body().getBanners().size());
            }

            @Override
            public void onFailure(Call<ResponsBanner> call, Throwable t) {

            }
        });
    }

}
