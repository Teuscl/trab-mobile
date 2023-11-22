package com.example.myapplication.ui.umidade;

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
import com.example.myapplication.databinding.FragmentUmidadeBinding;

public class UmidadeFragment extends Fragment {

    private FragmentUmidadeBinding binding;
    private UmidadeViewModel umidadeViewModel;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUmidadeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.txtUmidade;

        // Obtenha o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Log.d("UmidadeFragment", "onCreateView");

        // Observe as mudanças no LiveData
        sharedViewModel.getValorUmidade().observe(getViewLifecycleOwner(), umidade -> {
            textView.setText(umidade);
            Log.d("UmidadeFragment", "LiveData onChanged: " + umidade);
        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
