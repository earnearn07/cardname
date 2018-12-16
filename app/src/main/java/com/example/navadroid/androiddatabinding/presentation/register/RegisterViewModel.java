package com.example.navadroid.androiddatabinding.presentation.register;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.example.navadroid.androiddatabinding.data.entity.User;
import com.example.navadroid.androiddatabinding.data.repository.impl.RegisterRepositoryImpl;

public class RegisterViewModel extends ViewModel {

    private RegisterRepositoryImpl registerRepository = new RegisterRepositoryImpl();
    State state = new State(
            new MutableLiveData<Boolean>(),
            new MutableLiveData<Boolean>());
    MutableLiveData isRegister = new MutableLiveData<Boolean>();
    MutableLiveData isHasUser = new MutableLiveData<Boolean>();

    public void register(User user) {
        state.registerLoading.setValue(true);
        registerRepository.register(user).observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                state.registerLoading.setValue(false);
                isRegister.postValue(b);
            }
        });
    }

    public void hasUser(String username) {
        state.hasUserLoading.setValue(true);
        registerRepository.hasUser(username).observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                state.hasUserLoading.setValue(false);
                isHasUser.postValue(b);
            }
        });
    }

    public class State {
        final MutableLiveData<Boolean> registerLoading;
        final MutableLiveData<Boolean> hasUserLoading;
        State(MutableLiveData<Boolean> registerLoading, MutableLiveData<Boolean> hasUserLoading) {
            this.registerLoading = registerLoading;
            this.hasUserLoading = hasUserLoading;
        }
    }
}
