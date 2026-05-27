package com.example.medhelp.Model;

public class catlog_model_home_fragment {
    private int catImage;
    private String catName;

    public catlog_model_home_fragment(int catImage, String catName) {
        this.catImage = catImage;
        this.catName = catName;
    }

    public int getCatImage() {
        return catImage;
    }

    public void setCatImage(int catImage) {
        this.catImage = catImage;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
