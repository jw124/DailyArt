package com.example.dailyart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private String userID;
    private TextView welcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);
        userID = sharedPref.getString("UserID", "");
        if (userID.length() != 0) {
            welcomeView = (TextView) findViewById(R.id.welcome_view);
            welcomeView.setText("Welcome, " + userID + "!");
        }
        else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Welcome! Your User ID is xxx. You can share the ID to others to log in to your page. You can find this ID on the Profile page.")
                    .setTitle("Your User ID")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
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