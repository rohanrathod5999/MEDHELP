package com.example.medhelp.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.telephony.TelephonyCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medhelp.MedProduct;
import com.example.medhelp.R;
import com.example.medhelp.databinding.ItemCartBinding;
import com.example.medhelp.databinding.QuantityDialogBinding;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<MedProduct> products;
    CartListener cartListener;
    Cart cart;

    public interface CartListener{
        public void onQuantityChanged();
    }


    public CartAdapter(Context context, ArrayList<MedProduct> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        MedProduct product = products.get(position);

        String imageUrl = product.getImages().get(0);
        Glide.with(context)
                .load(imageUrl)
                .into(holder.binding.cartListImg);
        holder.binding.name.setText(product.getName());
        holder.binding.price.setText(product.getSalePriceDecimal());
//        holder.binding.ProductId.setText(product.getProductId());
//        holder.binding.ProductMaxQuantity.setText(product.getMaxQuantity());
//        holder.binding.quantity.setText(product.getMaxQuantity());
        holder.binding.quantity.setText(product.getQuantity()+" items(s)");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));


                quantityDialogBinding.productName.setText(product.name);
                quantityDialogBinding.productStock.setText("Stock: " + product.getMaxQuantity());
                quantityDialogBinding.quantity.setText(String.valueOf(product.getQuantity()));
                int stock = product.getMaxQuantity();


                quantityDialogBinding.plusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int quantity = product.getQuantity();
                        quantity++;

                        product.setQuantity(quantity);
                        quantityDialogBinding.quantity.setText(String.valueOf(quantity));
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    }

                });
                quantityDialogBinding.minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int quantity = product.getQuantity();
                        if(quantity > 1)
                            quantity--;
                        product.setQuantity(quantity);
                        quantityDialogBinding.quantity.setText(String.valueOf(quantity));
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    }

                });
                quantityDialogBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void deleteItem(int position){
        products.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());

    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding binding;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
