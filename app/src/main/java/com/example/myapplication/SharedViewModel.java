package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
        private final MutableLiveData<String> valorTemperatura = new MutableLiveData<>();
        private final MutableLiveData<String> valorPressao = new MutableLiveData<>();
        private final MutableLiveData<String> valorUmidade = new MutableLiveData<>();
        private final MutableLiveData<String> timestamp = new MutableLiveData<>();

    public MutableLiveData<String> getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp.postValue(timestamp);
        Log.d("SharedViewModel", "Timestamp atualizada: " + timestamp);
    }
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
        this.valorTemperatura.postValue(temperatura + "°C");
        Log.d("SharedViewModel", "Temperatura atualizada: " + temperatura);

    }

    public void setValorPressao(String pressao) {
        this.valorPressao.postValue(pressao + "kPa");
        Log.d("SharedViewModel", "Pressão atualizada: " + pressao);

    }

    public void setValorUmidade(String umidade) {
        //Realiza a conversão do valor recebido para porcentagem.
        double conversao = Double.parseDouble(umidade);
        conversao = conversao * 100;
        //Arredonda o valor para evitar muitas casas decimais.
        conversao = Math.round(conversao*100)/100.0;
        umidade = Double.toString(conversao);


        this.valorUmidade.postValue(umidade + "%");
        Log.d("SharedViewModel", "Umidade atualizada: " + umidade);

    }
}
