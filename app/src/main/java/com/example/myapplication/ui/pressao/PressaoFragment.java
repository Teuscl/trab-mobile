package com.example.myapplication.ui.pressao;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.SharedViewModel;
import com.example.myapplication.databinding.FragmentPressaoBinding;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PressaoFragment extends Fragment {

    private FragmentPressaoBinding binding;
    private SharedViewModel sharedViewModel;
    private String ultimaLeitura;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPressaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.txtPressao;
        final TextView time = binding.txtUltimaLeitura;

        // Obtenha o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        Log.d("PressaoFragment", "onCreateView");

        // Observe as mudanças no LiveData
        sharedViewModel.getValorPressao().observe(this, pressao -> {
            textView.setText(pressao);


        });

        sharedViewModel.getTimestamp().observe(getViewLifecycleOwner(), timestamp -> {
            time.setText("Última leitura: " + timestamp);
        });

        return root;
    }




   @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
