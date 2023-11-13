package com.example.tsb_kudeapp.ui.mezuak;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HornitzaileakViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HornitzaileakViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("SALMENTA");
    }

    public LiveData<String> getText() {
        return mText;
    }
}