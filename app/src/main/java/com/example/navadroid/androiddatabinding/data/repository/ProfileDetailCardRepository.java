package com.example.navadroid.androiddatabinding.data.repository;

import android.arch.lifecycle.LiveData;

public interface ProfileDetailCardRepository {
    LiveData<Boolean> delete(String username);
}
