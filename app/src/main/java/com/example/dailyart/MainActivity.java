package com.example.dailyart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private String userID;
    private TextView welcomeView;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TagView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        setAlarm();
        ArrayList<String> userTags = SharedPrefUtil.getStringList(getApplicationContext(),"USER_TAGS");
        if(userTags == null){
            userTags = new ArrayList<String>(Arrays.asList(new String[]{"General", "MileStone"}));
            SharedPrefUtil.saveStringList(getApplicationContext(),userTags,"USER_TAGS");
        }

        String [] prompts = {"Waterfall", "Mountains", "Birds", "Streetscape"};
        int promptIndex = 0;
        sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);
        userID = sharedPref.getString("UserID", "");
        tv = (TagView) findViewById(R.id.interactive_gallery);
        String[] milestones = {"MileStone"};
        this.displayImages(milestones);

        TextView randomPrompt = (TextView) findViewById(R.id.generated_prompt);
        randomPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int ix = rand.nextInt(prompts.length);
                randomPrompt.setText(prompts[ix]);
            }
        });



        Button uploadBtn = (Button) findViewById(R.id.upload_button);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openUploads = new Intent(getApplicationContext(), Upload.class);
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.INTERNET,
                                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        1);
                startActivity(openUploads);
            }
        });

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
        welcomeView.setText("Welcome! User ID: " + userID + ".");
    }

    private void setAlarm() {
        Calendar calender=Calendar.getInstance();
        calender.set(2022, 10, 26, 13, 37, 50);
        long systemTime = System.currentTimeMillis();
        long selectTime = calender.getTimeInMillis();
        if(systemTime > selectTime) {
            calender.add(Calendar.DAY_OF_MONTH, 1);
        }
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
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

    private void displayImages(String[] tagsArr){
        ArrayList<ArtworkModel> ams = new ArrayList<ArtworkModel>();
        for (int i = 0; i < tagsArr.length; i++){
            String currDir = Environment.getExternalStorageDirectory().toString() + "/Daily Art/Files/" + tagsArr[i] + "/";
            File directory = new File(currDir);
            File[] files = directory.listFiles();
            if (files != null) {
                for (int j = 0; j < files.length; j++) {
                    if (files[j].getName().endsWith(".jpg") ||
                            files[j].getName().endsWith(".jpg") ||
                            files[j].getName().endsWith(".png")) {
                        Log.d("IMAGE PATH", files[i].getName());
                        ams.add(new ArtworkModel("image " + j,
                                currDir + files[j].getName(),
                                "This is a good art",
                                new ArrayList<String>(Arrays.asList(tagsArr)),
                                new Date(),
                                currDir + files[j].getName().substring(0, files[j].getName().lastIndexOf('.')) + ".txt"));
                    }
                }
            }
        }
        TagViewAdapter tva = new TagViewAdapter(this, ams);
        tv.setGalleryAdapter(tva);
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
        } else if(v.getId() == R.id.settings_button){
            intent = new Intent(this, SettingsActivity.class);
        } else if (v.getId() == R.id.past_works_button){
            intent = new Intent(this, PastWorkActivity.class);
        } else if (v.getId() == R.id.milestone_button){
            intent = new Intent(this,Milestone.class);
        }
        startActivity(intent);
    }
}