package com.example.dailyart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private String userID;
    private TextView welcomeView;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        setAlarm();

        sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);
        userID = sharedPref.getString("UserID", "");

        // new user
        if (userID.length() == 0) {
            this.generateID();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Welcome! Your User ID is " + userID + ". You can share the ID to others to log in to your page. You can find this ID on the Profile page.")
                    .setTitle("Your User ID")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("UserID", String.valueOf(userID));
                            editor.apply();
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }

        welcomeView = (TextView) findViewById(R.id.welcome_view);
        welcomeView.setText("Welcome, " + userID + "!");
    }

    private void setAlarm() {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.SECOND, 5);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);
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

    public void generateID() {
        File file = new File(getBaseContext().getFilesDir(), "usersDB.json");
        String outputString = "";
        try {
            // exists other users
            if(file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                String response = stringBuilder.toString();

                JSONObject jsonObject = new JSONObject(response);
                int usersCount = jsonObject.getInt("usersCount");
                userID = Integer.toString(usersCount + 1);
                jsonObject.put("usersCount", usersCount + 1);

                JSONArray usersArray = jsonObject.getJSONArray("users");
                JSONObject newUserObject = new JSONObject();
                newUserObject.put("userID", userID);
                newUserObject.put("userName", "");
                usersArray.put(newUserObject);

                outputString = jsonObject.toString();
            }

            // first user of the app
            else {
                userID = new String("1");

                JSONObject jsonObject = new JSONObject();
                JSONArray usersArray = new JSONArray();
                JSONObject newUserObject = new JSONObject();
                newUserObject.put("userID", userID);
                newUserObject.put("userName", "");
                usersArray.put(newUserObject);
                jsonObject.put("users", usersArray);
                jsonObject.put("usersCount", 1);

                outputString = jsonObject.toString();
            }

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(outputString);
        bufferedWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMainNavClick(View v) {
        Intent intent = null;
        if(v.getId() == R.id.profile_button){
            intent = new Intent(this, ProfileActivity.class);
        }
        else if(v.getId() == R.id.settings_button){
            intent = new Intent(this, SettingsActivity.class);
        }
        startActivity(intent);
    }
}