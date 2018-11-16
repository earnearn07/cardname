package com.example.navadroid.androiddatabinding;

import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private String string;

    public MainViewModel(){
        this.string = "constructor";
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
