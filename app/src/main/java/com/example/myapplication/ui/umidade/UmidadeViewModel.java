package com.example.myapplication.ui.umidade;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UmidadeViewModel extends ViewModel {

    private final MutableLiveData<String> valorUmidade;

    public UmidadeViewModel() {
        valorUmidade = new MutableLiveData<>();
        valorUmidade.setValue("Fragmento para umidade");
    }

    public LiveData<String> getText() {
        return valorUmidade;
    }

    public void updateUmidade(String novaUmidade){
        valorUmidade.setValue(novaUmidade);
    }
}