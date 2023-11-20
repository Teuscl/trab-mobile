package com.example.myapplication.ui.pressao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.SharedViewModel;
import com.example.myapplication.databinding.FragmentPressaoBinding;

public class PressaoFragment extends Fragment {

    private FragmentPressaoBinding binding;
    private PressaoViewModel pressaoViewModel;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pressaoViewModel = new ViewModelProvider(this).get(PressaoViewModel.class);
        binding = FragmentPressaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.txtPressao;
        pressaoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Obtenha ou crie o ViewModel específico para este fragmento





        return root;
    }


   /* @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.txtPressao);

        // Obtenha o ViewModel compartilhado
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getValorPressao().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String pressao) {
                Log.d("PressaoFragment", "LiveData onChanged: " + pressao);
                // Atualize a interface do usuário com os dados recebidos
                textView.setText(pressao);

                // Além disso, se necessário, você pode repassar os dados para o ViewModel específico
                pressaoViewModel.updatePressao(pressao);
            }
        });

    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
