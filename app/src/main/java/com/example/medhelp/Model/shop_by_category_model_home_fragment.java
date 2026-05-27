package com.example.medhelp.Model;

public class shop_by_category_model_home_fragment {
    private String categoryProductName;
    private int categoryProductImage;

    public shop_by_category_model_home_fragment( int categoryProductImage,String categoryProductName) {
        this.categoryProductName = categoryProductName;
        this.categoryProductImage = categoryProductImage;
    }

    public String getCategoryProductName() {
        return categoryProductName;
    }

    public void setCategoryProductName(String categoryProductName) {
        this.categoryProductName = categoryProductName;
    }

    public int getCategoryProductImage() {
        return categoryProductImage;
    }

    public void setCategoryProductImage(int categoryProductImage) {
        this.categoryProductImage = categoryProductImage;
    }
}
