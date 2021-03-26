package com.kjyao.clooudinteractive;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kjyao.clooudinteractive.adapter.RespondsAdapter;
import com.kjyao.clooudinteractive.bean.RespondData;
import com.kjyao.clooudinteractive.viewmodel.DataViewModel;

import java.util.ArrayList;

public class DisplayedActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewResponds;
    private RespondsAdapter mRespondsAdapter;
    private DataViewModel mDataViewModel;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayed);
        setViewModel();
        setView();
        getData();
    }

    private void setView() {
        mRespondsAdapter = new RespondsAdapter(getApplicationContext(), new RespondsAdapter.ItemClickListener() {
            @Override
            public void onClick(View v, RespondData data) {
                Bundle bundle = new Bundle();
                bundle.putStringArray("passData", new String[]{data.getId(), data.getTitle(), data.getThumbnailUrl()});

                Intent intent = new Intent();
                intent.setClass(DisplayedActivity.this, ContentActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRespondsAdapter.setDataList(null);
        mRecyclerViewResponds = findViewById(R.id.recycler_view_responds);
        mRecyclerViewResponds.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4, RecyclerView.VERTICAL, false));
        mRecyclerViewResponds.setAdapter(mRespondsAdapter);

        mDialog = new AlertDialog.Builder(DisplayedActivity.this)
                .setTitle(null)
                .setMessage(R.string.loading)
                .setCancelable(false)
                .create();
    }

    private void setViewModel() {
        if (mDataViewModel != null) {
            return;
        }
        mDataViewModel = new ViewModelProvider(DisplayedActivity.this).get(DataViewModel.class);
        mDataViewModel.getDataResponse().observe(DisplayedActivity.this, new Observer<ArrayList<RespondData>>() {
            @Override
            public void onChanged(ArrayList<RespondData> result) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                if (result != null) {
                    mRespondsAdapter.setDataList(result);
                } else {
                    Toast.makeText(getApplicationContext(), "Get Data Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData() {
        mDialog.show();
        mDataViewModel.getData();
    }
}
