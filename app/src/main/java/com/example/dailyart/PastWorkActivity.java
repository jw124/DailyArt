package com.example.dailyart;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PastWorkActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    TagView tv;
    String[] userTags = {"Editable", "Camera"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_works); //setContentView(R.layout.activity_past_works);

        tv = (TagView) findViewById(R.id.interactive_gallery);

        Button sortByDate = (Button) findViewById(R.id.sort_by_date);
        sortByDate.setOnClickListener(this);


        Spinner spinner = (Spinner) findViewById(R.id.available_tags);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,userTags);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(this);

    }

    private void displayImages(String[] tagsArr){
        ArrayList<ArtworkModel> ams = new ArrayList<ArtworkModel>();
        for (int i = 0; i < tagsArr.length; i++){
            String currDir = "/sdcard/DCIM/" + tagsArr[i] + "/";
            File directory = new File(currDir);
            File[] files = directory.listFiles();
            for (int j = 0; j < 10; j++)
            {
                if (files[j].getName().endsWith(".jpg") ||
                        files[j].getName().endsWith(".jpg") ||
                        files[j].getName().endsWith(".png")) {
                    Log.d("IMAGE PATH", files[i].getName());
                    ams.add(new ArtworkModel("image " + j,
                            currDir + files[j].getName(),
                            "This is a bad photo",
                            new ArrayList<String>(Arrays.asList(tagsArr)),
                            new Date(),
                            currDir + files[j].getName().substring(0, files[j].getName().lastIndexOf('.')) + ".txt"));
                }
            }
        }
        TagViewAdapter tva = new TagViewAdapter(this, ams);
        tv.setGalleryAdapter(tva);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedTag = (String) parent.getItemAtPosition(pos);
        Toast.makeText(getApplicationContext(),selectedTag,Toast.LENGTH_SHORT).show();

        Log.d("SELECTED TAG", "selected callback invoked");
        String[] tagsArr = {selectedTag};
        this.displayImages(tagsArr);


    }

    @Override
    public void onClick(View view) {
        this.displayImages(userTags);
    }
}
