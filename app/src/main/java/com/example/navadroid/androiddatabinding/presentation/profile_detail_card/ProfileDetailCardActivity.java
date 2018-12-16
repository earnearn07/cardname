package com.example.navadroid.androiddatabinding.presentation.profile_detail_card;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.navadroid.androiddatabinding.R;
import com.example.navadroid.androiddatabinding.presentation.login.LoginActivity;

public class ProfileDetailCardActivity extends AppCompatActivity {

    ProfileDetailCardViewModel viewModel = new ProfileDetailCardViewModel();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_card);
        initView();
        observeView();
        observeData();
    }

    public void initView() {
        //get the data from the previous activity by using intent
        //show the data to the view
        TextView tvName = findViewById(R.id.tvName);
        ImageView ivProfile = findViewById(R.id.ivProfile);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvTel = findViewById(R.id.tvTel);

        final String username = getIntent().getStringExtra("username");
        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        String address = getIntent().getStringExtra("address");
        String tel = getIntent().getStringExtra("tel");

        tvName.setText(name);
        tvAddress.setText(address);
        tvTel.setText(tel);
        showImage(ivProfile, image);

        TextView tvDelete = findViewById(R.id.tvDelete);
        if (retrieveUserData().equals(username)) {
            tvDelete.setVisibility(View.VISIBLE);
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = initLoadingDialog();
                    viewModel.delete(username);
                }
            });
        } else {
            tvDelete.setVisibility(View.INVISIBLE);
        }
    }

    private void observeView() {
        viewModel.state.deleteLoading.observe(this, new Observer<Boolean>() {
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
        viewModel.isDelete.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDelete) {
                if (isDelete) {
                    showRemovedUserAlert();
                    clearUserDate();
                } else {
                    showMessageError();
                }
            }
        });
    }

    private void showMessageError() {
        Toast.makeText(this, getString(R.string.profile_detail_card_delete_failed), Toast.LENGTH_SHORT).show();
    }

    //using the glide for showing the image easily
    private void showImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .circleCrop();

        Glide.with(imageView)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(imageView);
    }

    private String retrieveUserData() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        return prefs.getString("username", null);
    }

    private void clearUserDate() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    private void navigateTo(Activity activity) {
        Intent intent = new Intent(this, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private ProgressDialog initLoadingDialog() {
        String title = getString(R.string.profile_detail_card_dialog_title);
        String message = getString(R.string.profile_detail_card_progress_dialog_message);
        dialog = ProgressDialog.show(this, title, message, true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void showRemovedUserAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.profile_detail_card_dialog_title));
        alertDialog.setMessage(getString(R.string.profile_detail_card_dialog_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.profile_detail_card_dialog_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        navigateTo(new LoginActivity());
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
