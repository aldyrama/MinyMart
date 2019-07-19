package com.example.minymart.fragment;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.minymart.Base.BaseFragment;
import com.example.minymart.R;
import com.example.minymart.adapter.SliderAdapter;
import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.apihelper.UtilsApi;
import com.example.minymart.model.Banner;
import com.example.minymart.model.respons.ResponsBanner;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerFragment extends BaseFragment {

    private SliderView mSlider;
    BaseApiService mApiService;
    List<Banner> mBanner;
    SliderAdapter mSliderAdpter;
    View view;
    ProgressBar mPb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);

        attachView(view);
        mApiService = UtilsApi.getApiService();
        mBanner = new ArrayList<>();
        mSlider = view.findViewById(R.id.imageSlider);
        mPb = view.findViewById(R.id.pb);
        mSliderAdpter = new SliderAdapter(getContext(), mBanner);
        mSlider.setSliderAdapter(mSliderAdpter);
        mSlider.setIndicatorAnimation(IndicatorAnimations.SLIDE);
        mSlider.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        mSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        mSlider.setIndicatorSelectedColor(Color.WHITE);
        mSlider.setIndicatorUnselectedColor(Color.GRAY);
        mSlider.startAutoCycle();
        if (isOnline()){
            loadBanner();
        }
        else {

            mPb.setVisibility(View.GONE);
        }

        mSlider.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                mSlider.setCurrentPagePosition(position);
            }
        });

        return view;

    }

    public void loadBanner(){
        mPb.setVisibility(View.VISIBLE);
        Call<ResponsBanner> call = mApiService.getBanner();
        call.enqueue(new Callback<ResponsBanner>() {
            @Override
            public void onResponse(Call<ResponsBanner> call, Response<ResponsBanner> response) {
                Log.d("banner", "respons " + response.body().getBanners().size());
                mBanner.clear();
                mBanner.addAll(response.body().getBanners());
                mSliderAdpter.notifyDataSetChanged();
                mPb.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponsBanner> call, Throwable t) {

            }
        });
    }



}
