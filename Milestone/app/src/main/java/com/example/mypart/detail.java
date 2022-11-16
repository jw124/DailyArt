package com.example.mypart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class detail extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView=imageView.findViewById(R.id.image);
        textView=textView.findViewById(R.id.critique);

        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            String selectedName=intent.getStringExtra("name");
            int selectedImage=intent.getIntExtra("image",0);

            textView.setText(selectedName);
            imageView.setImageResource(selectedImage);
        }
    }
}