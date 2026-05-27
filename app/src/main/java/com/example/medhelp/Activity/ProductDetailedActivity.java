package com.example.medhelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.hishd.tinycart.model.Cart;
//import com.hishd.tinycart.tinycartlib.models.TinyCartItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.medhelp.MedProductAdapter;
import com.example.medhelp.MedProduct;
import com.example.medhelp.R;
import com.example.medhelp.databinding.ActivityProductDetailedBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;
//import com.hishd.tinycart.util.TinyCartHelper;


public class ProductDetailedActivity extends AppCompatActivity {

    ActivityProductDetailedBinding binding;
    MedProduct currentMedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detailed);
        binding = ActivityProductDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTheme(R.style.ProductDetailActivityTheme);

        MedProduct product = (MedProduct) getIntent().getSerializableExtra("product");

        // Set product details in the layout
        binding.productDetailedName.setText(product.getName());
        binding.productDetailedManufacture.setText("Manufactured By: " +product.getManufacturer());
        binding.productDetailedPrice.setText("₹: " +product.getSalePriceDecimal());
//        binding.productDetailedId.setText("ID: "+product.getProductId());

//     binding.productDetailedMaxQuantity.setText("Stock: "+product.getMaxQuantity());

        // Use Glide or other image loading library to load the product image
        String imageUrl = product.getImages().get(0);
        Glide.with(this)
                .load(imageUrl)
                .into(binding.productDetailedImage);
        getSupportActionBar().setTitle(product.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MedProduct currentMedProduct = new MedProduct();
        currentMedProduct.setName(product.getName());
        currentMedProduct.setSalePriceDecimal(product.getSalePriceDecimal());
        currentMedProduct.setQuantity(1); // Set the initial quantity to 1
        currentMedProduct.setImages(product.getImages());
//        MedProductAdapter adapter = new MedProductAdapter(currentMedProduct);
        binding.AddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MedProductAdapter adapter = new MedProductAdapter(currentMedProduct);
                Cart cart = TinyCartHelper.getCart();
                cart.addItem(currentMedProduct,1);
                binding.AddToCartBtn.setEnabled(false);
                binding.AddToCartBtn.setText("Added In Cart");
//                Toast.makeText(ProductDetailedActivity.this, "Product added to cart.", Toast.LENGTH_SHORT).show();
            }
        });






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart){
            MedProduct product =(MedProduct) getIntent().getSerializableExtra("product");


            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}