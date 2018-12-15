package com.example.navadroid.androiddatabinding.presentation.profile_detail_card;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.navadroid.androiddatabinding.R;

public class ProfileDetailCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_card);
        initView();
    }

    public void initView() {
        //get the data from the previous activity by using intent
        //show the data to the view
        TextView tvName = findViewById(R.id.tvName);
        ImageView ivProfile = findViewById(R.id.ivProfile);

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");

        tvName.setText(name);
        showImage(ivProfile, image);

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
}
