package com.example.myapplication;

import android.util.Log;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

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
        long timestampL = Long.parseLong(timestamp) * 1000; // Convertendo para milissegundos

        //realiza a conversão do timestamp para um formato legivel
        Date data = new Date(timestampL);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String dataFormatada = sdf.format(data);

        this.timestamp.postValue(dataFormatada);
        Log.d("SharedViewModel", "Timestamp atualizada: " + dataFormatada);
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
        //Arredonda o valor para evitar muitas casas decimais.
        conversao = Math.round(conversao*100)/100.0;
        umidade = Double.toString(conversao);


        this.valorUmidade.postValue(umidade + "%");
        Log.d("SharedViewModel", "Umidade atualizada: " + umidade + "%");
    }

}
