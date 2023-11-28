package com.example.myapplication;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MqttMain {

    private String client_username, client_password;
    final String host = "80fe29ce8268427c9a4a9aeb6cabf603.s2.eu.hivemq.cloud";
    private ArrayList<String> valores = new ArrayList<String>();


    public MqttMain(String username, String password) {
        this.client_username = username;
        this.client_password = password;
    }

    public interface MqttConnectListener{
        void onConnectionSucess();
        void onConnectionFailure();
        void onNewDataReceived(String data);

    }
    @SuppressLint("StaticFieldLeak")
    public void connect(Context context, MqttConnectListener listener) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    final Mqtt5BlockingClient client = MqttClient.builder()
                            .useMqttVersion5()
                            .serverHost(host)
                            .serverPort(8883)
                            .sslWithDefaultConfig()
                            .buildBlocking();

                    client.connectWith()
                            .simpleAuth()
                            .username(client_username)
                            .password(UTF_8.encode(client_password))
                            .applySimpleAuth()
                            .send();

                    client.subscribeWith()
                            .topicFilter("dados")
                            .send();

                    //Função assincrona que fica escutando cada payload que chega no broker e transforma essa mensagem para um formato legível
                    client.toAsync().publishes(ALL, publish -> {
                        Log.d("MQTT","Received message: " +
                                publish.getTopic() + " -> " +
                                UTF_8.decode(publish.getPayload().get()));
                        String timestamp = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(ZonedDateTime.now(ZoneId.of("GMT-3")));

                        //transforma o payload em UTF_8 e atribui a variavel newData, e concatena com o timestamp do momento em que foi recebido
                        String newData = UTF_8.decode(publish.getPayload().get()).toString() + ";" + timestamp;

                        //notifica o listener com o novo payload recebido
                        listener.onNewDataReceived(newData);
                    });
                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }


            //Metodo que ocorre imediatamente após o doInBackground, e trata o retorno da função que rodou em background
            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    listener.onConnectionSucess();

                } else {
                    listener.onConnectionFailure();
                }
            }
        }.execute();
    }

}