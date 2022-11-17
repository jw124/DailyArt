package com.example.dailyart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.dailyart.R;

public class Milestone extends AppCompatActivity {
    Button galleryButton,achievementsButton;
    ProgressBar mypb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//给btn1，btn2赋值，即设置布局文件中的Button按钮id进行关联
        galleryButton = (Button) findViewById(R.id.galleryButton);
        achievementsButton = (Button) findViewById(R.id.achievementsButton);
        //为button绑定事件
//        galleryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Milestone.this,M_Gallery.class);
//                startActivity(intent);
//            }
//        });
        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Milestone.this,Achievements.class);
                startActivity(intent);
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Milestone.this,M_Gallery.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void load(View view) {
        int progress=mypb.getProgress();
        progress=10;
        mypb.setProgress(progress);
    }
}