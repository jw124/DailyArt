package com.example.dailyart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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