package com.example.medhelp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.medhelp.MedProduct;
import com.example.medhelp.MedProductRepository;

import java.util.List;

public class ordermedicineViewModel extends AndroidViewModel {

    private MedProductRepository repository;

    public ordermedicineViewModel(@NonNull Application application) {
        super(application);
        this.repository = new MedProductRepository();
    }

//    LiveData
    public LiveData<List<MedProduct>> getAllMedProduct(){
        return repository.getMutableLiveData();
    }
}
