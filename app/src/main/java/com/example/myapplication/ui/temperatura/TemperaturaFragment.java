package com.example.myapplication.ui.temperatura;

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
import com.example.myapplication.databinding.FragmentTemperaturaBinding;

public class TemperaturaFragment extends Fragment {

    private FragmentTemperaturaBinding binding = null;
    private TemperaturaViewModel temperaturaViewModel;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTemperaturaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.txtTemperatura;

        // Obtenha o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Log.d("TemperaturaFragment", "onCreateView");

        // Observe as mudanças no LiveData
        sharedViewModel.getValorTemperatura().observe(getViewLifecycleOwner(), temperatura -> {
            // Atualize a interface do usuário com os dados recebidos
            textView.setText(temperatura);
            Log.d("TemperaturaFragment", "LiveData onChanged: " + temperatura);
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
