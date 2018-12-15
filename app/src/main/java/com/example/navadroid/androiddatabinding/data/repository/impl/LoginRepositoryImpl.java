package com.example.navadroid.androiddatabinding.data.repository.impl;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.navadroid.androiddatabinding.data.entity.User;
import com.example.navadroid.androiddatabinding.data.repository.LoginRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginRepositoryImpl implements LoginRepository {

    //this function to retrieve data from the firebase
    //this function retrieve to check user permission to use this application
    @Override
    public LiveData<User> login(final String username) {
        return new LiveData<User>() {
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
                                    User user = dataSnapshot.getValue(User.class);
                                    user.setUsername(dataSnapshot.getKey());
                                    setValue(user);
                                } else {
                                    setValue(null);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                setValue(null);
                            }
                        });
            }
        };
    }
}