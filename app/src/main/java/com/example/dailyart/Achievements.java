package com.example.dailyart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

public class Achievements extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private ProgressBar share5Bar, add5Bar, share25Bar, add10Bar, share50Bar, add50Bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        share5Bar = findViewById(R.id.share_5_bar);
        add5Bar = findViewById(R.id.add_5_bar);
        share25Bar = findViewById(R.id.share_25_bar);
        add10Bar = findViewById(R.id.add_10_bar);
        share50Bar = findViewById(R.id.share_50_bar);
        add50Bar = findViewById(R.id.add_50_bar);
        sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);

        setBars();
    }

    private void setBars() {
        int shareCount = sharedPref.getInt("ShareCount", 0);
        share5Bar.setProgress(100 * shareCount / 5);
        share25Bar.setProgress(100 * shareCount / 25);
        share50Bar.setProgress(100 * shareCount / 50);

        int galleryCount = sharedPref.getInt("GalleryCount", 0);
        add5Bar.setProgress(100 * galleryCount / 5);
        add10Bar.setProgress(100 * galleryCount / 10);
        add50Bar.setProgress(100 * galleryCount / 50);
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