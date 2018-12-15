package com.example.navadroid.androiddatabinding.presentation.user_list;

import android.arch.lifecycle.Observer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.navadroid.androiddatabinding.R;
import com.example.navadroid.androiddatabinding.data.entity.User;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private UserListViewModel viewModel = new UserListViewModel();
    private SwipeRefreshLayout srUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initView();
        observeView();
        observeData();
        viewModel.list();
    }

    //In lifecycle of android, view must to create first before data are retrieved
    //we use SwipeRefreshLayout to refresh the data from the firebase
    private void initView() {
        srUserList = findViewById(R.id.srUserList);
        srUserList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.list();
            }
        });
    }

    private void observeView() {
        viewModel.state.userListLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                srUserList.setRefreshing(isLoading);
            }
        });
    }

    private void observeData() {
        viewModel.userList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                if (userList != null) {
                    initRecyclerView(userList);
                } else {
                    showErrorMessage();
                }
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.user_list_no_user), Toast.LENGTH_SHORT).show();
    }

    //this function create the recycler view to show the user list
    private void initRecyclerView(List<User> userList) {
        RecyclerView rvUserList = findViewById(R.id.rvUserList);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        rvUserList.setAdapter(new UserListAdapter(userList, this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                rvUserList.getContext(),
                LinearLayoutManager.VERTICAL);
        rvUserList.addItemDecoration(dividerItemDecoration);
    }
}
