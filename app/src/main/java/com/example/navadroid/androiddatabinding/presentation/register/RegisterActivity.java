package com.example.navadroid.androiddatabinding.presentation.register;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.navadroid.androiddatabinding.R;
import com.example.navadroid.androiddatabinding.data.entity.User;

public class RegisterActivity extends AppCompatActivity {
    RegisterViewModel viewModel = new RegisterViewModel();
    private ProgressDialog dialog;
    EditText etUsername;
    EditText etPassword;
    EditText etFirstName;
    EditText etLastName;
    EditText etImageUrl;
    EditText etAddress;
    EditText etTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        observeView();
        observeData();
    }

    private void initView() {
        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyField()) {
                    toast(getString(R.string.register_empty_field));
                } else {
                    dialog = initLoadingDialog();
                    viewModel.hasUser(etUsername.getText().toString().toLowerCase());
                }
            }
        });
    }

    private void observeView() {
        viewModel.state.hasUserLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    dialog.show();
                } else {
                    dialog.dismiss();
                }
            }
        });
        viewModel.state.registerLoading.observe(this, new Observer<Boolean>() {
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

    private void observeData() {
        viewModel.isHasUser.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isHasUser) {
                if(isHasUser) {
                    toast(getString(R.string.register_username_exists));
                } else {
                    User user = new User(
                            etLastName.getText().toString(),
                            etPassword.getText().toString(),
                            etFirstName.getText().toString(),
                            etUsername.getText().toString().toLowerCase(),
                            etImageUrl.getText().toString(),
                            etAddress.getText().toString(),
                            etTel.getText().toString()
                    );
                    viewModel.register(user);
                }
            }
        });
        viewModel.isRegister.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegister) {
                if (isRegister) {
                    toast(getString(R.string.register_registered_success));
                    finish();
                } else {
                    toast(getString(R.string.register_registered_failed));
                }
            }
        });
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private ProgressDialog initLoadingDialog() {
        String title = getString(R.string.register_dialog_title);
        String message = getString(R.string.register_dialog_message);
        dialog = ProgressDialog.show(this, title, message, true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private Boolean isEmptyField() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etImageUrl = findViewById(R.id.etImageUrl);
        etAddress = findViewById(R.id.etAddress);
        etTel = findViewById(R.id.etTel);

        return etUsername.getText().toString().equals("")
                || etPassword.getText().toString().equals("")
                || etFirstName.getText().toString().equals("")
                || etLastName.getText().toString().equals("")
                || etImageUrl.getText().toString().equals("")
                || etAddress.getText().toString().equals("")
                || etTel.getText().toString().equals("");
    }
}
