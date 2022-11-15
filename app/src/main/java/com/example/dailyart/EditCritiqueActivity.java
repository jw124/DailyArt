package com.example.dailyart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class EditCritiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_critique);

        ImageView iv = (ImageView) findViewById(R.id.focused_image);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            Uri j = (Uri) b.get("IMAGE_FILEPATH");
            Log.d("RECEIVED PATH", j.toString());
            iv.setImageURI(j);
        }
    }
}