package com.example.tsb_kudeapp.ui.CRM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CRMViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CRMViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("CRM");
    }

    public LiveData<String> getText() {
        return mText;
    }
}