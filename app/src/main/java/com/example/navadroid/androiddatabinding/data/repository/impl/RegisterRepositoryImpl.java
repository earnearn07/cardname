package com.example.navadroid.androiddatabinding.data.repository.impl;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.navadroid.androiddatabinding.data.entity.User;
import com.example.navadroid.androiddatabinding.data.repository.RegisterRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterRepositoryImpl implements RegisterRepository {

    @Override
    public LiveData<Boolean> register(final User user) {
        return new LiveData<Boolean>() {
            @Override
            protected void onActive() {
                super.onActive();
                String username = user.getUsername();
                user.setUsername(null);
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child(username)
                        .setValue(user)
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

    @Override
    public LiveData<Boolean> hasUser(final String username) {
        return new LiveData<Boolean>() {
            @Override
            protected void onActive() {
                super.onActive();
                FirebaseDatabase
                        .getInstance()
                        .getReference(username)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    postValue(true);
                                } else {
                                    postValue(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                postValue(false);
                            }
                        });
            }
        };
    }
}
