package com.example.myapplication.ui.pressao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PressaoViewModel extends ViewModel {

    private final MutableLiveData<String> valorPressao;

    public PressaoViewModel() {
        valorPressao = new MutableLiveData<>();
        valorPressao.setValue("Fragmento para pressão");
    }

    public LiveData<String> getText() {
        return valorPressao;
    }

    public void updatePressao(String novaPressao){
        valorPressao.setValue(novaPressao);
    }
}