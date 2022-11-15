package com.example.dailyart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button getImageBtn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    
    public void onclick(View view) {
        Intent intent = new Intent(this,Upload.class);
        startActivity(intent);
    }
}