package com.example.medhelp;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedProductRepository {
//    used to abstract the data source details and
//     provides a clean api for the viewmodel to
//    fetch and manage data

    private ArrayList<MedProduct> medProducts = new ArrayList<>();
    private MutableLiveData<List<MedProduct>> mutableLiveData = new MutableLiveData<>();


    public MutableLiveData<List<MedProduct>> getMutableLiveData() {
        MedProductApiService medProductApiService = RetrofitInstance.getMedProductApiService();
        Call<Products> call = medProductApiService.getHealthCareProducts();
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
//            success
                Products products = response.body();

                if (products!= null & products.getProducts() != null){
                    medProducts = (ArrayList<MedProduct>)  products.getProducts();
                    mutableLiveData.setValue(medProducts);
                }

            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
