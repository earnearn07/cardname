package com.example.navadroid.androiddatabinding.data.repository;

import android.arch.lifecycle.LiveData;
import com.example.navadroid.androiddatabinding.data.entity.User;

public interface LoginRepository {
    LiveData<User> login(String username);
}
