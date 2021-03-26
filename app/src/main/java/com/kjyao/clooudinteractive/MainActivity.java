package com.kjyao.clooudinteractive;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button mBtnRequestAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }

    private void setView() {
        mBtnRequestAPI = findViewById(R.id.btn_request_api);
        mBtnRequestAPI.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this, DisplayedActivity.class);
            startActivity(intent);
        });
    }
}