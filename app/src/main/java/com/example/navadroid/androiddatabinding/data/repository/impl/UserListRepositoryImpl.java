package com.example.navadroid.androiddatabinding.data.repository.impl;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.navadroid.androiddatabinding.data.entity.User;
import com.example.navadroid.androiddatabinding.data.repository.UserListRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UserListRepositoryImpl implements UserListRepository {

    @Override
    public LiveData<List<User>> list() {
        return new LiveData<List<User>>() {
            @Override
            protected void onActive() {
                super.onActive();
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    List<User> userList = new ArrayList<>();
                                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                                        User user = data.getValue(User.class);
                                        user.setUsername(data.getKey());
                                        userList.add(user);
                                    }
                                    setValue(userList);
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
