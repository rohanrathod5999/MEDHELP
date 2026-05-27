package com.example.medhelp;

import static androidx.databinding.library.baseAdapters.BR.name;
import static androidx.databinding.library.baseAdapters.BR.salePriceDecimal;

import com.example.medhelp.MedProduct;
import com.hishd.tinycart.model.Item;

import java.math.BigDecimal;

public class MedProductAdapter implements Item {
    private MedProduct medProduct;
    public  MedProductAdapter(MedProduct medProduct){
        this.medProduct =medProduct;
    }
    @Override
    public BigDecimal getItemPrice() {
// Parse the salePriceDecimal to a double
        // Parse the salePriceDecimal to a double
        double doubleValue = Double.parseDouble(medProduct.salePriceDecimal);

        // Create a BigDecimal object from the parsed double value
        BigDecimal itemPrice = BigDecimal.valueOf(doubleValue);

        return itemPrice;
    }

    @Override
    public String getItemName() {
        return medProduct.name;
    }
}
