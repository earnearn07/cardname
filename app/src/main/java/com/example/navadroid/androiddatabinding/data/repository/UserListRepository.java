package com.example.navadroid.androiddatabinding.data.repository;

import android.arch.lifecycle.LiveData;

import com.example.navadroid.androiddatabinding.data.entity.User;
import java.util.List;

public interface UserListRepository {
    LiveData<List<User>> list();
}
