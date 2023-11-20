package com.example.myapplication;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

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
                            .topicFilter("dados/")
                            .send();

                    client.toAsync().publishes(ALL, publish -> {
                        System.out.println("Received message: " +
                                publish.getTopic() + " -> " +
                                UTF_8.decode(publish.getPayload().get()));
                        String newData = UTF_8.decode(publish.getPayload().get()).toString();
                        listener.onNewDataReceived(newData);


                        //client.disconnect();
                    });



                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

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