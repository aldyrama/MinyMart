package com.example.minymart.model.respons;

import com.example.minymart.model.Cart;
import com.example.minymart.model.Data;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponsCart implements Serializable {

    @SerializedName("data")
    private Data carts;

    public Data getCarts() {
        return carts;
    }

    public void setCarts(Data carts) {
        this.carts = carts;
    }
}
