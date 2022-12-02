package com.example.dailyart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

public class Achievements extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private ProgressBar shareBar, milestoneBar, uploadBar;
    private TextView shareText, milestoneText, uploadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shareBar = findViewById(R.id.share_bar);
        shareText = findViewById(R.id.share_text);
        milestoneBar = findViewById(R.id.milestone_bar);
        milestoneText = findViewById(R.id.milestone_text);
        uploadBar = findViewById(R.id.upload_bar);
        uploadText = findViewById(R.id.upload_text);
        sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);

        setBars();
    }

    private void setBars() {
        String userID = sharedPref.getString("UserID", "");

        int shareCount = sharedPref.getInt("ShareCount_" + userID, 0);
        int shareLevel = shareCount / 5;
        int shareRemainder = shareCount % 5;
        shareText.setText("Share " + String.valueOf(5 * (shareLevel + 1)) + " Works!");
        shareBar.setProgress(100 * shareRemainder / 5);

        int milestoneCount = countFiles(Environment.getExternalStorageDirectory() + "/Daily Art/Files/" + "user_" + userID + "/MileStone");
        int milestoneLevel = milestoneCount / 5;
        int milestoneRemainder = milestoneCount % 5;
        milestoneText.setText("Set " + String.valueOf(5 * (milestoneLevel + 1)) + " Milestones!");
        milestoneBar.setProgress(100 * milestoneRemainder / 5);

        int pastWorkCount = countFiles(Environment.getExternalStorageDirectory() + "/Daily Art/Files/" + "user_" + userID + "/General");
        int pastWorkLevel = pastWorkCount / 10;
        int pastWorkRemainder = pastWorkCount % 10;
        uploadText.setText("Upload " + String.valueOf(10 * (pastWorkLevel + 1)) + " Works");
        uploadBar.setProgress(100 * pastWorkRemainder / 10);
    }

    private int countFiles(String path){
        int count = 0;
        File dir = new File(path);
        File files[] = dir.listFiles();
        if (files == null){
            return 0;
        }
        for (File f : files){
            if (f.getName().contains(".jpg")){
                count ++;
            }
        }
        return count;
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
}