package com.example.tsb_kudeapp.ui.salmentak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tsb_kudeapp.databinding.FragmentSalmentaBinding;

public class SalmentaFragment extends Fragment {

    private FragmentSalmentaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SalmentaViewModel salmentaViewModel =
                new ViewModelProvider(this).get(SalmentaViewModel.class);

        binding = FragmentSalmentaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}