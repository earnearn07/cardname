package com.example.navadroid.androiddatabinding.data.repository.impl;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.navadroid.androiddatabinding.data.repository.ProfileDetailCardRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileDetailCardRepositoryImpl implements ProfileDetailCardRepository {
    @Override
    public LiveData<Boolean> delete(final String username) {
        return new LiveData<Boolean>() {
            @Override
            protected void onActive() {
                super.onActive();
                FirebaseDatabase
                        .getInstance()
                        .getReference(username)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                postValue(true);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                               postValue(false);
                            }
                        });
            }
        };
    }
}
