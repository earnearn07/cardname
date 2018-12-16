package com.example.navadroid.androiddatabinding.presentation.profile_detail_card;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.example.navadroid.androiddatabinding.data.repository.impl.ProfileDetailCardRepositoryImpl;

public class ProfileDetailCardViewModel extends ViewModel {

    private ProfileDetailCardRepositoryImpl loginRepository = new ProfileDetailCardRepositoryImpl();
    State state = new State(new MutableLiveData<Boolean>());
    MutableLiveData isDelete = new MutableLiveData<Boolean>();

    public void delete(String username) {
        state.deleteLoading.setValue(true);
        loginRepository.delete(username).observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                state.deleteLoading.setValue(false);
                isDelete.postValue(b);
            }
        });
    }

    public class State {
        final MutableLiveData<Boolean> deleteLoading;

        State(MutableLiveData<Boolean> deleteLoading) {
            this.deleteLoading = deleteLoading;
        }
    }
}
