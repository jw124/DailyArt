package com.example.dailyart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Editable inputId;
    private EditText inputArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputArea = (EditText) findViewById(R.id.input_area);
        inputId = inputArea.getText();
    }
}