package com.example.dailyart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Editable inputId;
    private EditText inputArea;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputArea = (EditText) findViewById(R.id.input_area);
        inputId = inputArea.getText();
    }

    public void displayDialog(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (inputId.length() == 0) {
            dialogBuilder.setMessage("Please input a valid User ID.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
        }
        else {
            dialogBuilder.setMessage("Log in as " + inputId + "?")
                    .setTitle("Confirm Log In ID")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("UserID", String.valueOf(inputId));
                            editor.apply();
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
        }
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}