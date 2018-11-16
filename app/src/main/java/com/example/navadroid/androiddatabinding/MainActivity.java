package com.example.navadroid.androiddatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.TextView;

import com.example.navadroid.androiddatabinding.databinding.ActivityMainBindingImpl;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    // TODO Step 1: Declare binding instance instead view's (binding class is auto-generated)
    //private TextView textView;
    ActivityMainBindingImpl binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        viewModel = new MainViewModel();

        // TODO Step 2: Use DataBindingUtil instead of findViewById
        //textView = findViewById(R.id.tv_display);
        //textView.setText(viewModel.getString());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewmodel(viewModel);

    }

    public void click(View view) {
        long time = System.currentTimeMillis();
        if(time%2==0)
            viewModel.setString("even");
        else
            viewModel.setString("odd");

        // TODO Step 3: Use binding
        //textView.setText(viewModel.getString());
        binding.tvDisplay.setText(viewModel.getString());
    }
}
