package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

        //Recebe a action bar presente na MainActivity para poder oculta-la
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Instancia o shared viewmodel na MainActivity
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        username = (EditText) findViewById(R.id.edtLogin);
        password = (EditText) findViewById(R.id.password);

        //Cria ou recupera a sharedpreference chamada dados. MODE_PRIVATE indica que somente o aplicativo que o criou pode acessar
        SharedPreferences sharedPref = getSharedPreferences("dados", MODE_PRIVATE);
        //Obtem o editor do sharedpreferences para poder editar os dados
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
                        //Implementa a interface OnConnectionSucess e a OnConnectionFailure, que trata se a conexão deu certo ou errado
                        @Override
                        public void onConnectionSucess() {
                            //Caso a conexão tenha ocorrido com sucesso, exibe um toast personalizado e inicia a DashboardActivity.
                            showToastSuccessful();
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            // Fecha a atividade atual (MainActivity)
                            finish();
                        }

                        @Override
                        public void onConnectionFailure() {
                            //Exibe um toast personalizado caso ocorra erro na conexão
                            showToastError();
                        }

                        //implementa a interface para tratar o que fazer a cada novo payload recebido
                        @Override
                        public void onNewDataReceived(String data) {
                            // Lida com os novos dados recebidos, separando-os em um array e atribuindo seus valores separadamente
                            String[] dataArray = data.split(";");
                            Log.d("MQTT", "Dados: " + data);
                            String temperatura = dataArray[0];
                            String umidade = dataArray[1];
                            String pressao = dataArray[2];
                            String timestamp = dataArray[3];

                            //Insere esses valores no editor do sharedpreferences e confirma as alterações
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

    private void showToastSuccessful() {
        //cria um layout personalizado para exibir no toast no caso de sucesso de login
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_login_sucessful, null);
        ImageView icon = layout.findViewById(R.id.toast_icon_sucessful);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void showToastError() {
        //cria um layout personalizado para exibir no toast no caso de erro de login
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_login_fail, null);
        ImageView icon = layout.findViewById(R.id.toast_icon_error);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
