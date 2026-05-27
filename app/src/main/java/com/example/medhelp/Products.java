package com.example.medhelp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @SerializedName("products")
    @Expose
    private List<MedProduct> products = null;

    public List<MedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<MedProduct> products) {
        this.products = products;
    }

    public Products() {    }

}
