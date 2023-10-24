package com.example.tsb_kudeapp.ui.stock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StockViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StockViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("STOCK");
    }

    public LiveData<String> getText() {
        return mText;
    }
}