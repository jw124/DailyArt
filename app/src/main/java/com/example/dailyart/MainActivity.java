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
        welcomeView.setText("Welcome, " + userID + "!");
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
        }
        else if(v.getId() == R.id.settings_button){
            intent = new Intent(this, SettingsActivity.class);
        }
        startActivity(intent);
    }
}