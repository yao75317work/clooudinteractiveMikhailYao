package com.kjyao.clooudinteractive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.kjyao.clooudinteractive.glide.GlideApp;

public class ContentActivity extends AppCompatActivity {
    private ImageView mImageBackground;
    private TextView mTextId, mTextTitle;
    private String[] mPassStringData = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initial();
        setView();
    }

    private void initial() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mPassStringData = bundle.getStringArray("passData");
            }
        }
    }

    private void setView() {
        mImageBackground = findViewById(R.id.image_background);
        mTextId = findViewById(R.id.text_id);
        mTextTitle = findViewById(R.id.text_title);

        mImageBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideApp.with(getApplicationContext()).clear(mImageBackground);
                finish();
            }
        });

        if (mPassStringData != null) {
            mTextId.setText(mPassStringData[0]);
            mTextTitle.setText(mPassStringData[1]);

            String url = mPassStringData[2];
            GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                    .addHeader("User-Agent",
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                    .build());

            GlideApp.with(getApplicationContext())
                    .load(glideUrl)
                    .fitCenter()
                    .into(mImageBackground);
        }
    }
}
