package com.example.minymart.model.respons;

import com.example.minymart.model.Banner;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponsBanner implements Serializable {

    @SerializedName("data")
    private ArrayList<Banner> banners;

    public ArrayList<Banner> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<Banner> banners) {
        this.banners = banners;
    }
}
