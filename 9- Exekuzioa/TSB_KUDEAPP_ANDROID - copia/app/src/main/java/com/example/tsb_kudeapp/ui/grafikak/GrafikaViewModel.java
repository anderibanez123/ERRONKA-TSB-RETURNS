package com.example.tsb_kudeapp.ui.grafikak;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GrafikaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GrafikaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("GRAFIKA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}