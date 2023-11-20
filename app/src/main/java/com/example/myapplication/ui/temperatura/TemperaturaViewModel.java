package com.example.myapplication.ui.temperatura;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TemperaturaViewModel extends ViewModel {

    private final MutableLiveData<String> valorTemperatura;

    public TemperaturaViewModel() {
        valorTemperatura = new MutableLiveData<>();
        valorTemperatura.setValue("Fragmento para temperatura");
    }

    public LiveData<String> getText() {
        return valorTemperatura;
    }
    public void updateTemperatura(String novaTemperatura){
        valorTemperatura.setValue(novaTemperatura);
    }
}