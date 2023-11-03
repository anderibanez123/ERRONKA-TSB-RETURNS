package com.example.tsb_kudeapp.ui.salmentak;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalmentaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SalmentaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("SALMENTA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}