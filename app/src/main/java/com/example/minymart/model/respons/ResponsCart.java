package com.example.minymart.model.respons;

import com.example.minymart.model.Cart;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponsCart implements Serializable {

    @SerializedName("data")
    private ArrayList<Cart> carts;

    public ArrayList<Cart> getCarts() {
        return carts;
    }

    public void setCarts(ArrayList<Cart> carts) {
        this.carts = carts;
    }
}
