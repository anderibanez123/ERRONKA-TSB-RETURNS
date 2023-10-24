package com.example.tsb_kudeapp.ui.CRM;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tsb_kudeapp.databinding.FragmentCrmBinding;

public class CRMFragment extends Fragment {

    private FragmentCrmBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CRMViewModel CRMViewModel =
                new ViewModelProvider(this).get(CRMViewModel.class);

        binding = FragmentCrmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}