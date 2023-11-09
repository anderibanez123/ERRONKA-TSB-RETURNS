package com.example.tsb_kudeapp.ui.mezuak;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MezuaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MezuaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("SALMENTA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}