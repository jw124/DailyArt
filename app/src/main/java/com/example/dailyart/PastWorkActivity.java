package com.example.dailyart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PastWorkActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    TagView tv;
//    String[] userTags = {"MileStone", "General","Normal"};
    ArrayList<String> userTags;
    private ProgressBar mypb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);
        String userID = sharedPref.getString("UserID", "");
        setContentView(R.layout.activity_past_works); //setContentView(R.layout.activity_past_works);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userTags = SharedPrefUtil.getStringList(getBaseContext(),"USER_TAGS_"+userID);
        if(userTags == null){
            userTags = new ArrayList<String>(Arrays.asList(new String[]{"General", "MileStone"}));
            SharedPrefUtil.saveStringList(getApplicationContext(),userTags,"USER_TAGS_"+userID);
        }

        tv = (TagView) findViewById(R.id.interactive_gallery);

        Button sortByDate = (Button) findViewById(R.id.sort_by_date);
        sortByDate.setOnClickListener(this);


        Spinner spinner = (Spinner) findViewById(R.id.available_tags);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,userTags);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(this);

    }

    private void displayImages(ArrayList<String> tagsArr){
        SharedPreferences sharedPref = getSharedPreferences("CurrentUserID", MODE_PRIVATE);
        String userID = sharedPref.getString("UserID", "");
        ArrayList<ArtworkModel> ams = new ArrayList<ArtworkModel>();
        for (int i = 0; i < tagsArr.size(); i++){
            String currDir = Environment.getExternalStorageDirectory().toString() + "/Daily Art/Files/" + "user_"+ userID +"/"+ tagsArr.get(i) + "/";
            File directory = new File(currDir);
            File[] files = directory.listFiles();
            if (files != null) {
                for (int j = 0; j < files.length; j++) {
                    if (files[j].getName().endsWith(".jpg") ||
                            files[j].getName().endsWith(".jpg") ||
                            files[j].getName().endsWith(".png")) {
                        Log.d("IMAGE PATH", files[i].getName());
                        ams.add(new ArtworkModel("image " + j,
                                currDir + files[j].getName(),
                                "This is a good art",
                                tagsArr,
                                new Date(),
                                currDir + files[j].getName().substring(0, files[j].getName().lastIndexOf('.')) + ".txt"));
                    }
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
        ArrayList<String> tagsArr = new ArrayList<String>(Arrays.asList(new String[]{selectedTag}));
        this.displayImages(tagsArr);


    }

    @Override
    public void onClick(View view) {
        this.displayImages(userTags);
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

    public void load(View view) {
        int progress=mypb.getProgress();
        progress=10;
        mypb.setProgress(progress);
    }
}
