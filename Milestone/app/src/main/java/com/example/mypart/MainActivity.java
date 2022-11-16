package com.example.mypart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    Button galleryButton,achievementsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//给btn1，btn2赋值，即设置布局文件中的Button按钮id进行关联
        galleryButton = (Button) findViewById(R.id.galleryButton);
        achievementsButton = (Button) findViewById(R.id.achievementsButton);
        //为button绑定事件
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,M_Gallery.class);
                startActivity(intent);
            }
        });
        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Achievements.class);
                startActivity(intent);
            }
        });


        Intent intent = new Intent(this, AutoReceiver.class);
        intent.setAction("VIDEO_TIMER");
        // PendingIntent这个类用于处理即将发生的事情
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        // AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
        // SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), 10 * 1000, sender);


    }
}