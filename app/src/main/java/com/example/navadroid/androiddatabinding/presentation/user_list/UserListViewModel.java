package com.example.navadroid.androiddatabinding.presentation.user_list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import com.example.navadroid.androiddatabinding.data.entity.User;
import com.example.navadroid.androiddatabinding.data.repository.impl.UserListRepositoryImpl;

import java.util.List;

public class UserListViewModel extends ViewModel {

    private UserListRepositoryImpl userListRepository = new UserListRepositoryImpl();
    State state = new State(new MutableLiveData<Boolean>());
    MutableLiveData userList = new MutableLiveData<List<User>>();

    public void list() {
        state.userListLoading.setValue(true);
        userListRepository.list().observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                state.userListLoading.setValue(false);
                userList.postValue(users);
            }
        });
    }

    public class State {
        final MutableLiveData<Boolean> userListLoading;

        State(MutableLiveData<Boolean> userListLoading) {
            this.userListLoading = userListLoading;
        }
    }
}


