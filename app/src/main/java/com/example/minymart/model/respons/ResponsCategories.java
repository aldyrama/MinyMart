package com.example.minymart.model.respons;

import com.example.minymart.model.Category;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponsCategories implements Serializable {

    @SerializedName("data")
    private ArrayList<Category> categorie;

    public ArrayList<Category> getCategorie() {
        return categorie;
    }

    public void setCategorie(ArrayList<Category> categorie) {
        this.categorie = categorie;
    }
}
