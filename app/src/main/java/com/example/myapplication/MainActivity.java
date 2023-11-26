package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText username, password;
    private SharedViewModel sharedViewModel;

    String username_input, password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        username = (EditText) findViewById(R.id.edtLogin);
        password = (EditText) findViewById(R.id.password);

        SharedPreferences sharedPref = getSharedPreferences("dados", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_input = username.getText().toString().trim();
                password_input = password.getText().toString();

                //verifica se os campos são nulos
                 if( username_input.isEmpty() && password_input.isEmpty()){
                    password.setError("O campo não pode ser vazio!");
                    username.setError("O campo não pode ser vazio!");

                }
                else if(username_input.isEmpty()){
                    username.setError("O campo não pode ser vazio!");
                } else if (password_input.isEmpty()) {
                    password.setError("O campo não pode ser vazio!");

                }

                //se não forem, tenta realizar a conexão
                else{
                    MqttMain handler = new MqttMain(username_input, password_input);
                    handler.connect(MainActivity.this, new MqttMain.MqttConnectListener() {
                        @Override
                        public void onConnectionSucess() {
                            Toast.makeText(MainActivity.this, "Login bem-sucedido", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            // Fecha a atividade atual (MainActivity)
                            finish();
                        }

                        @Override
                        public void onConnectionFailure() {
                            Toast.makeText(MainActivity.this, "Erro na tentativa de criar o Cliente MQTT. Tente novamente!", Toast.LENGTH_LONG).show();

                        }

                        //implementa a interface para tratar o que fazer a cada novo payload recebido
                        @Override
                        public void onNewDataReceived(String data) {
                            // Lide com os novos dados recebidos, por exemplo, atualize a UI ou compartilhe com a DashboardActivity
                            String[] dataArray = data.split(";");
                            Log.d("MQTT", "Dados: " + data);
                            String temperatura = dataArray[0];
                            String umidade = dataArray[1];
                            String pressao = dataArray[2];
                            String timestamp = dataArray[3];


                            Log.d("MQTT", "Dados recebidos: " + temperatura + ", " + umidade + ", " + pressao + "," + timestamp);
                            editor.putString("temperatura", temperatura);
                            editor.putString("pressao", pressao);
                            editor.putString("umidade", umidade);
                            editor.putString("timestamp", timestamp);
                            editor.apply();
                        }
                    });

                 }

            }
        });

    }

}
