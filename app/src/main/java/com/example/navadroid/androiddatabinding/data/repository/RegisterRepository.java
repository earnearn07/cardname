package com.example.navadroid.androiddatabinding.data.repository;

import android.arch.lifecycle.LiveData;

import com.example.navadroid.androiddatabinding.data.entity.User;

public interface RegisterRepository {
    LiveData<Boolean> register(User user);
    LiveData<Boolean> hasUser(String username);
}
