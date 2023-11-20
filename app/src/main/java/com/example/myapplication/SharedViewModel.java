package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
        private final MutableLiveData<String> valorTemperatura = new MutableLiveData<>();
        private final MutableLiveData<String> valorPressao = new MutableLiveData<>();
        private final MutableLiveData<String> valorUmidade = new MutableLiveData<>();


    public MutableLiveData<String> getValorTemperatura() {
        return valorTemperatura;
    }

    public MutableLiveData<String> getValorPressao() {
        return valorPressao;
    }

    public MutableLiveData<String> getValorUmidade() {
        return valorUmidade;
    }

    public void setValorTemperatura(String temperatura) {
        this.valorTemperatura.postValue(temperatura);
        Log.d("SharedViewModel", "Temperatura atualizada: " + temperatura);

    }

    public void setValorPressao(String pressao) {
        this.valorPressao.postValue(pressao);
        Log.d("SharedViewModel", "Pressão atualizada: " + pressao);

    }

    public void setValorUmidade(String umidade) {
        this.valorUmidade.postValue(umidade);
        Log.d("SharedViewModel", "Umidade atualizada: " + umidade);

    }
}
