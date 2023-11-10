package com.example.tsb_kudeapp.ui.mezuak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tsb_kudeapp.databinding.FragmentMezuaBinding;

public class MezuaFragment extends Fragment {

    private FragmentMezuaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MezuaViewModel homeViewModel =
                new ViewModelProvider(this).get(MezuaViewModel.class);

        binding = FragmentMezuaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}