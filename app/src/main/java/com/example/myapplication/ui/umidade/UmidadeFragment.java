package com.example.myapplication.ui.umidade;

import android.os.Bundle;
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



        // Obtenha ou crie o ViewModel específico para este fragmento
        umidadeViewModel = new ViewModelProvider(this).get(UmidadeViewModel.class);



        return root;
    }
 /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);
        TextView txtUmidade = view.findViewById(R.id.txtUmidade);

        // Obtenha o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observe as mudanças no ViewModel compartilhado
        sharedViewModel.getValorUmidade().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String umidade) {

                Log.d("UmidadeFragment", "LiveData onChanged: " + umidade);
                // Atualize a interface do usuário com os dados recebidos
                txtUmidade.setText(umidade);

                // Além disso, se necessário, você pode repassar os dados para o ViewModel específico
                umidadeViewModel.updateUmidade(umidade);
            }
        });
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
