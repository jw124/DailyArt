package com.example.dailyart;
//

import android.content.Context;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
//

public class M_Gallery extends AppCompatActivity {
    GridView gridView;


    private ProgressBar mypb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = findViewById(R.id.GridView);

        ArrayList<ArtworkModel> ams = new ArrayList<ArtworkModel>();
        String currDir = Environment.getExternalStorageDirectory().toString() + "/Daily Art/Files/" + "MileStone" + "/";
        File directory = new File(currDir);
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith(".jpg") ||
                        files[i].getName().endsWith(".jpg") ||
                        files[i].getName().endsWith(".png")) {
                    Log.d("IMAGE PATH", files[i].getName());
                    CustomAdapter customAdapter = new CustomAdapter(currDir + files[i].getName(),files.length , this);
                }
            }


            gridView.setAdapter(customAdapter);


        }
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


    public class CustomAdapter extends BaseAdapter {
        private String[] imageNames;
        private int[] imagePhoto;
        private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[] imagePhoto, Context context) {
            this.imageNames = imageNames;
            this.imagePhoto = imagePhoto;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagePhoto.length;
        }

        @Override
        public Object getItem(int i) {
            return imagePhoto[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override


        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = layoutInflater.inflate(R.layout.item, viewGroup, false);
            }
            TextView critique = view.findViewById(R.id.critique);
            ImageView imageView = view.findViewById(R.id.image);

            critique.setText(imageNames[i]);
            imageView.setImageResource(imagePhoto[i]);



            return view;
        }

    }
}
















