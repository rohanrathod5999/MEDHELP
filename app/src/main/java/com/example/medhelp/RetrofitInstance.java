package com.example.medhelp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
//Acts as a centeral configurationn point for
//    defining how http request and responce
//    should be handled

    private static Retrofit retrofit = null;
//    private static String BASE_URL = "https://mysterious-belt-lamb.cyclic.app/";
    private static String BASE_URL = "https://repomedicine-production.up.railway.app/";

    public static MedProductApiService getMedProductApiService() {
       if (retrofit == null){
           retrofit = new Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
       }
       return retrofit.create(MedProductApiService.class);
    }


}
