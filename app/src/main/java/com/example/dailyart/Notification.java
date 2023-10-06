package com.example.dailyart;

import static android.app.AlarmManager.INTERVAL_DAY;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Notification extends AppCompatActivity {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notification.this,MainActivity.class);
                startActivity(intent);
            }
        });
        createNotificationChannel();
        //setAlarm();

    }

    private void setAlarm() {
        Calendar calender=Calendar.getInstance();
        int hour=calender.get(Calendar.HOUR_OF_DAY);
        int min=calender.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1){
                Calendar c=Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,i);
                c.set(Calendar.MINUTE,i1);


                long systemTime = System.currentTimeMillis();
                long selectTime = c.getTimeInMillis();
                if(systemTime > selectTime) {
                    c.add(Calendar.DAY_OF_MONTH, 60*24);
                }

                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                //pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),86400000, pendingIntent);

            }
        },hour,min,true);
        timePickerDialog.show();



    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "dailyappchannel";
            String description = "Channel for daily app";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("dailyapp", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createAlarm(View view){
        setAlarm();
    }

    public void cancelAlarm(View view){
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "You cancel it!", Toast.LENGTH_SHORT).show();
    }
}