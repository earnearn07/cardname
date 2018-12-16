package com.example.navadroid.androiddatabinding.presentation.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.navadroid.androiddatabinding.R;
import com.example.navadroid.androiddatabinding.data.entity.User;
import com.example.navadroid.androiddatabinding.presentation.register.RegisterActivity;
import com.example.navadroid.androiddatabinding.presentation.user_list.UserListActivity;

public class LoginActivity extends AppCompatActivity {
    //init ViewModel from here
    private LoginViewModel viewModel = new LoginViewModel();
    private EditText etUsername;
    private EditText etPassword;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        observeView();
        observeData();
    }

    private void initView() {
        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(new RegisterActivity());
            }
        });
    }

    //observe view to show progress dialog before data is coming from model
    private void observeView() {
        viewModel.state.loginLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    dialog.show();
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    //observe data from the model
    private void observeData() {
        viewModel.profile.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    if (etUsername.getText().toString().toLowerCase().equals(user.getUsername())
                            && etPassword.getText().toString().toLowerCase().equals(user.getPassword())) {
                        saveUserData();
                        navigateTo(new UserListActivity());
                    } else {
                        showErrorMessage();
                    }
                } else {
                    showErrorMessage();
                }
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
    }

    private ProgressDialog initLoadingDialog() {
        String title = getString(R.string.login_dialog_title);
        String message = getString(R.string.login_dialog_message);
        dialog = ProgressDialog.show(this, title, message, true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void navigateTo(Activity activity) {
        Intent intent = new Intent(this, activity.getClass());
        startActivity(intent);
    }

    //on click event by calling xml in Login
    public void onLoginClick(View view) {
        dialog = initLoadingDialog();
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        viewModel.login(etUsername.getText().toString().toLowerCase());
    }

    public void saveUserData() {
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("username", etUsername.getText().toString());
        editor.apply();
    }
}
