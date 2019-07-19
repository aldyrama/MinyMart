package com.example.minymart.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {

    @SerializedName("tx_id")
    private String tx_id;
    @SerializedName("total_price")
    private int total_price;
    @SerializedName("products")
    private ArrayList<Cart> products;

    public String getTx_id() {
        return tx_id;
    }

    public void setTx_id(String tx_id) {
        this.tx_id = tx_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public ArrayList<Cart> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Cart> products) {
        this.products = products;
    }
}
