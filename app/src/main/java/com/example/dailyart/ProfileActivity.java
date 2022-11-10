package com.example.dailyart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private Button loginButton;
    private SharedPreferences sharedPref;
    private String userID;
    private TextView userIDView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);
        userID = sharedPref.getString("UserID", "");
        if (userID.length() != 0) {
            userIDView = (TextView) findViewById(R.id.userid_view);
            userIDView.setText(userID);
        }

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}