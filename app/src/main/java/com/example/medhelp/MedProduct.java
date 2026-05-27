package com.example.medhelp;

//import com.hishd.tinycart.tinycartlib.models.TinyCartItem;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
        import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hishd.tinycart.model.Item;
//import com.hishd.tinycart.model.Item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class MedProduct extends BaseObservable implements Serializable, Item {
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;


//    @SerializedName("productId")
//    @Expose
//    public int productId;
public static final int PRODUCT_ID_CONSTANT = 3514609;


    @SerializedName("name")
    @Expose
    public String name;
//    public String slug;
@SerializedName("manufacturer")
@Expose
public String manufacturer;
//    public int productType;
@SerializedName("mrpDecimal")
@Expose
public String mrpDecimal;
@SerializedName("salePriceDecimal")
@Expose
public String salePriceDecimal;
    @SerializedName("discountDecimal")
    @Expose
    public String discountDecimal;

    @SerializedName("discountPercent")
    @Expose
    public String discountPercent;

    @SerializedName("maxQuantity")
    @Expose
    public int maxQuantity;



    @SerializedName("images")
    @Expose
    public ArrayList<String> images;

    @BindingAdapter({"images"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        String imagePath = imageUrl; // Removed the prefix
        Log.d("ImageURL", imagePath); // Log the corrected image URL
        Glide.with(imageView.getContext())
                .load(imagePath)
                .into(imageView);
    }
//    @BindingAdapter({"images"})
//    public static void loadImage(ImageView imageView, String imageUrl){
//        String imagePath ="https://mysterious-belt-lamb.cyclic.app/" +imageUrl;
//        Log.d("ImageURL", imagePath); // Add this line to log the image URL
//        Glide.with(imageView.getContext())
//                .load(imagePath)
//                .into(imageView);
//    }


    @Bindable
    public int getProductId() {
        return PRODUCT_ID_CONSTANT; // Return the constant product ID
    }

    public void setProductId(int productId) {
        // This method is not used for a constant product ID
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getManufacturer() {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        notifyPropertyChanged(BR.manufacturer);
    }

    @Bindable
    public String getMrpDecimal() {
        return mrpDecimal;
    }

    public void setMrpDecimal(String mrpDecimal) {
        this.mrpDecimal = mrpDecimal;
        notifyPropertyChanged(BR.mrpDecimal);

    }
    @Bindable
    public String getSalePriceDecimal() {
        return salePriceDecimal;
    }

    public void setSalePriceDecimal(String salePriceDecimal) {
        this.salePriceDecimal = salePriceDecimal;
        notifyPropertyChanged(BR.salePriceDecimal);

    }
    @Bindable
    public String getDiscountDecimal() {
        return discountDecimal;
    }

    public void setDiscountDecimal(String discountDecimal) {
        this.discountDecimal = discountDecimal;
        notifyPropertyChanged(BR.discountDecimal);

    }
    @Bindable
    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
        notifyPropertyChanged(BR.discountPercent);

    }
    @Bindable
    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
        notifyPropertyChanged(BR.maxQuantity);

    }

    @Bindable
    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Override
    public BigDecimal getItemPrice() {
// Parse the salePriceDecimal to a double
        double doubleValue = Double.parseDouble(salePriceDecimal);

        // Create a BigDecimal object from the parsed double value
        BigDecimal itemPrice = BigDecimal.valueOf(doubleValue);

        return itemPrice;
    }


    @Override
    public String getItemName() {
        return name;
    }


//    @Bindable
//    public String getImages() {
//        return images;
//    }
//
//    public void setImages(String images) {
//        this.images = images;
//        notifyPropertyChanged(BR.images);
//
//    }


//    public Object categoryId;

//    public LabelDetails labelDetails;
//    public RatingDetails ratingDetails;
//    public Object listingId;
//    public ProductTierAttributes productTierAttributes;
//    public Object productSubstitutionAttributes;
//    public ProductAvailabilityFlags productAvailabilityFlags;

//    public int productVolume;
//    public Object similarProductsAttributes;
//    public boolean isAvailable;


}