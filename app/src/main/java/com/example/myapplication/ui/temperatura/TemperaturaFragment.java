package com.example.myapplication.ui.temperatura;

import android.os.Bundle;
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

        temperaturaViewModel = new ViewModelProvider(this).get(TemperaturaViewModel.class);
        binding = FragmentTemperaturaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.txtTemperatura;
        temperaturaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // Obtenha ou crie o ViewModel específico para este fragmento
        return root;
    }

  /*  @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenha o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        TextView txtTemperatura = view.findViewById(R.id.txtTemperatura);

        // Observe as mudanças no ViewModel compartilhado
        sharedViewModel.getValorTemperatura().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String temperatura) {
                Log.d("TemperaturaFragment", "LiveData onChanged: " + temperatura);
                // Atualize a interface do usuário com os dados recebidos
                System.out.println("Viem model" + temperatura);
                txtTemperatura.setText(temperatura);

                // Além disso, se necessário, você pode repassar os dados para o ViewModel específico
                temperaturaViewModel.updateTemperatura(temperatura);
            }
        });

    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
