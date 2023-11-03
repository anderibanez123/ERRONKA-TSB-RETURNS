package com.example.tsb_kudeapp.ui.grafikak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tsb_kudeapp.databinding.FragmentGrafikoaBinding;

public class GrafikaFragment extends Fragment {

    private FragmentGrafikoaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GrafikaViewModel GrafikaViewModel =
                new ViewModelProvider(this).get(GrafikaViewModel.class);

        binding = FragmentGrafikoaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}