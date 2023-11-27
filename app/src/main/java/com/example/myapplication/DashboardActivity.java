package com.example.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.view.MenuItem;


import com.example.myapplication.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private SharedViewModel sharedViewModel;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_temperatura, R.id.navigation_umidade, R.id.navigation_pressao)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#213E8A")));

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        SharedPreferences sharedPref = getSharedPreferences("dados",MODE_PRIVATE);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {

                sharedViewModel.setValorUmidade(sharedPref.getString("umidade", "Nenhuma leitura disponível"));
                sharedViewModel.setValorPressao(sharedPref.getString("pressao", "Nenhuma leitura disponível"));
                sharedViewModel.setValorTemperatura(sharedPref.getString("temperatura", "Nenhuma leitura disponível"));
                sharedViewModel.setTimestamp(sharedPref.getString("timestamp", "Nenhum registro disponível"));
            }
        };

        sharedPref.registerOnSharedPreferenceChangeListener(listener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_update) {
            showToastSuccessful();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToastSuccessful() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_sobre, null);

        ImageView icon = layout.findViewById(R.id.toast_icon_sucessful);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}





