package com.example.minymart.model.respons;

import com.example.minymart.model.Products;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponsProduct implements Serializable {

    @SerializedName("data")
    ArrayList<Products> products;

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }
}
