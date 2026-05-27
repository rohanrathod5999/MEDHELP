package com.example.medhelp.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.medhelp.utils.Constants;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.auth.FirebaseAuth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.example.medhelp.Adapter.CartAdapter;
import com.example.medhelp.MedProduct;
import com.example.medhelp.databinding.ActivityCheckOutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity  implements PaymentResultListener {
ActivityCheckOutBinding binding;
private BroadcastReceiver smsReceiver;
    CartAdapter adapter;
    ArrayList<MedProduct> products;
    Cart cart;
    double totalPrice = 0;
    final int tax = 5;
    ProgressDialog progressDialog;
    private String orderId;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Checkout.preload(getApplicationContext());


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing.....");
        products = new ArrayList<>();
        cart = TinyCartHelper.getCart();
        for (Map.Entry<Item,Integer>item: cart.getAllItemsWithQty().entrySet()){
            MedProduct product = (MedProduct) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);
            products.add(product);
        }



        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.price.setText(String.format("₹: %.2f",cart.getTotalPrice()));
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.recyclerView3.setLayoutManager(layoutManager);
        binding.recyclerView3.addItemDecoration(itemDecoration);
        binding.recyclerView3.setAdapter(adapter);

        binding.price.setText(String.format("₹: %.2f",cart.getTotalPrice()));
        totalPrice = (cart.getTotalPrice().doubleValue() * tax/100) + cart.getTotalPrice().doubleValue();
        binding.total.setText("₹: "+ totalPrice);

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOrder();
            }
        });


        Checkout.preload(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerSmsReceiver();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
         unregisterReceiver();
    }

    private void unregisterReceiver() {
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    private void registerSmsReceiver() {
        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(smsReceiver,intentFilter);
    }



    void processOrder() {
        progressDialog.show();

        // Get user data
        String name = binding.nameBox.getText().toString();
        String address = binding.addressBox.getText().toString();
        String email = binding.emailBox.getText().toString();
        String phone = binding.phoneBox.getText().toString();
        String comment = binding.commentBox.getText().toString();

        // Prepare order data
        double subtotal = cart.getTotalPrice().doubleValue();
        double taxValue = subtotal * tax / 100;
        double total = subtotal + taxValue;
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();

        // Get the user ID from Firebase Authentication
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Order order = new Order(name, address, email, phone, comment, subtotal, taxValue, total, currentTime, userId);

        // Send order data to Firebase Realtime Database
        DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference("Registered Users")
                .child(userId)
                .child("orders")
                .push();
                orderRef.setValue(order, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                             orderId = ref.getKey(); // Retrieve the key directly from the DatabaseReference
                            Toast.makeText(CheckOutActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

                            // Show AlertDialog
                           alertDialog = new AlertDialog.Builder(CheckOutActivity.this)
                                    .setTitle("Order Placed")
                                    .setMessage("Your order has been placed successfully. Do you want to proceed to payment now?")
                                    .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // Proceed to PaymentActivity
//                                            Intent intent = new Intent(CheckOutActivity.this, PaymentActivity.class);
//                                            intent.putExtra("orderId", orderId); // Pass the actual order ID to PaymentActivity
//                                            startActivity(intent);
                                            startRaazorpayPayment(orderId);
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                   .create();
                                    alertDialog.show();

                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(CheckOutActivity.this, "Failed to place the order. Please try again.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });



        startRaazorpayPayment(orderId);

    }

    private void startRaazorpayPayment(String orderId) {

//        Initialize razorpay checkout
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_gwiUX9bsPDdthe");
        //        checkout.setKeyID("rzp_test_2kNynke2DhJg6");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "MedHelp");
            options.put("currency", "INR");
            double totalPrice = Double.parseDouble(binding.total.getText().toString().replace("₹: ", ""));

            int amountInPaise = (int)(totalPrice*100);
            options.put("amount", amountInPaise); // Replace with the actual amount
            options.put("order_id", orderId);
            if (options == null || options.length() == 0) {
                Log.e("CheckOutActivity", "Error: options is null or empty");
                return;
            }
            checkout.open(this, options);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("Razorpay Error", "Error starting Razorpay payment", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        // Payment successful, handle accordingly
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        if (alertDialog!= null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

    @Override
    public void onPaymentError(int errorCode, String errorDescription) {
        // Payment failed, handle accordingly
        String errorMessage = "Payment Failed: ";
        if (errorDescription != null && !errorDescription.isEmpty()) {
            errorMessage += errorDescription;
        } else {
            errorMessage += "Unknown error";
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    // Add a new class for Order
    public class Order {
        public String name;
        public String address;
        public String email;
        public String phone;
        public String comment;
        public double subtotal;
        public double tax;
        public double total;
        public long created_at;

        public Order(String name, String address, String email, String phone, String comment, double subtotal, double tax, double total, long created_at, String userId) {
            this.name = name;
            this.address = address;
            this.email = email;
            this.phone = phone;
            this.comment = comment;
            this.subtotal = subtotal;
            this.tax = tax;
            this.total = total;
            this.created_at = created_at;
        }
    }
//    void processOrder(){
//
//        progressDialog.show();
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        JSONObject productOrder = new JSONObject();
//        JSONObject dataObject = new JSONObject();
//
//        try {
//
//            productOrder.put("address",binding.addressBox.getText().toString());
//            productOrder.put("buyer",binding.nameBox.getText().toString());
//            productOrder.put("comment",binding.commentBox.getText().toString());
//            productOrder.put("Created_at", Calendar.getInstance().getTimeInMillis());
//            productOrder.put("last_update", Calendar.getInstance().getTimeInMillis());
//
//            productOrder.put("date_ship", Calendar.getInstance().getTimeInMillis());
//            productOrder.put("email",binding.emailBox.getText().toString());
//            productOrder.put("phone",binding.phoneBox.getText().toString());
//            productOrder.put("serial","cab8c1a4e4421a3b");
//            productOrder.put("shipping","");
//            productOrder.put("shipping_location","");
//            productOrder.put("shipping_rate","0.0");
//            productOrder.put("tax",tax);
//            productOrder.put("total_fees",totalPrice);
//            JSONArray product_order_detail = new JSONArray();
//            for (Map.Entry<Item,Integer>item: cart.getAllItemsWithQty().entrySet()){
//                MedProduct product = (MedProduct) item.getKey();
//                int quantity = item.getValue();
//                product.setQuantity(quantity);
//
//
//                JSONObject productOBJ = new JSONObject();
//                productOBJ.put("amount",quantity);
//                productOBJ.put("price_item", product.getSalePriceDecimal());
//                productOBJ.put("product_id", product.getProductId());
//                productOBJ.put("product_name", product.getName());
//                product_order_detail.put(productOBJ);
//            }
//
//            dataObject.put("product_order",productOrder);
//            dataObject.put("product_order_detail",product_order_detail);
//
//            Log.e("err",dataObject.toString());
//
//        }catch (JSONException e) {}
//
//
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.POST_ORDER_URL, dataObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    if (response.getString("status").equals("success")){
//                        Toast.makeText(CheckOutActivity.this, "Success order.", Toast.LENGTH_SHORT).show();
////order failed becuase of conflict between our json data and postman data
////                        String orderNumber = response.getJSONObject("data").getString("code");
//
//                        String orderNumber = ("dfdff343ds");
//                        new AlertDialog.Builder(CheckOutActivity.this)
//                                .setTitle("Order Succesful")
//                                .setCancelable(false)
//                                .setMessage("Your order Number is:" + orderNumber)
//                                .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        Intent intent = new Intent(CheckOutActivity.this,PaymentActivity.class);
//                                        intent.putExtra("orderCode",orderNumber);
//                                        startActivity(intent);
//                                    }
//                                }).show();
//                   Log.e("res",response.toString());
//                    }else {
//                        Toast.makeText(CheckOutActivity.this, "Success order", Toast.LENGTH_SHORT).show();
////                        Toast.makeText(CheckOutActivity.this, "Failed order", Toast.LENGTH_SHORT).show();
////                        Log.e("res",response.toString());
//                        String OrderNumber = ("dfdff343ds");
//                        new AlertDialog.Builder(CheckOutActivity.this)
//                                .setTitle("Order Succesful")
//                                .setCancelable(false)
//                                .setMessage("Your order Number is:" + OrderNumber)
//                                .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        Intent intent = new Intent(CheckOutActivity.this,PaymentActivity.class);
//                                        intent.putExtra("orderCode",OrderNumber);
//                                        startActivity(intent);
//
//                                    }
//                                }).show();
//                    }
//                    progressDialog.dismiss();
//                    Log.e("res",response.toString());
//
//
//                }catch (Exception e){}
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String , String > headers = new HashMap<>();
//                headers.put("Security","secure_code");
//                return headers;
//            }
//        };
//        queue.add(request);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}