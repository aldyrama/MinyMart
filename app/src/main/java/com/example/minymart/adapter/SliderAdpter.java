package com.example.minymart.adapter;

import com.example.minymart.apihelper.BaseApiService;
import com.example.minymart.model.Banner;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class SliderAdpter extends SliderAdapter{

    BaseApiService mApiService;
    List<Banner> mBanner;

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
//        Banner banner = mBanner.get(position);
//        int data = Integer.parseInt(banner.getImage());
//        for(int i=0; i < data; i++) {
//            viewHolder.bindImageSlide(i);
////        }
        switch (position) {
            case 0:
                viewHolder.bindImageSlide("http://lorempixel.com/480/270/city/1/");
                break;
            case 1:
                viewHolder.bindImageSlide( "http://lorempixel.com/480/270/city/2/");
                break;
            case 2:
                viewHolder.bindImageSlide("http://lorempixel.com/480/270/city/3/");
                break;
            case 3:
                viewHolder.bindImageSlide("http://lorempixel.com/480/270/city/4/");
                break;
            case 4:
                viewHolder.bindImageSlide( "http://lorempixel.com/480/270/city/5/");
                break;
        }
    }
}
