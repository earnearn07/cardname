package com.example.navadroid.androiddatabinding.presentation.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import com.example.navadroid.androiddatabinding.data.entity.User;
import com.example.navadroid.androiddatabinding.data.repository.impl.LoginRepositoryImpl;

public class LoginViewModel extends ViewModel {

    private LoginRepositoryImpl loginRepository = new LoginRepositoryImpl();
    State state = new State(new MutableLiveData<Boolean>());
    MutableLiveData profile = new MutableLiveData<User>();

    //this function uses to connect with repository and view
    public void login(String username) {
        state.loginLoading.setValue(true);
        loginRepository.login(username).observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                state.loginLoading.setValue(false);
                profile.postValue(user);
            }
        });
    }

    public class State {
        final MutableLiveData<Boolean> loginLoading;

        State(MutableLiveData<Boolean> loginLoading) {
            this.loginLoading = loginLoading;
        }
    }
}


