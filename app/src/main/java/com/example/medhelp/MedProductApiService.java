package com.example.medhelp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MedProductApiService {
    @GET("/healthCareProductsPage")
    Call<Products> getHealthCareProducts();


}
