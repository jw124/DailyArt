package com.example.dailyart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

public class EditCritiqueActivity extends AppCompatActivity {
    private ProgressBar mypb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_critique);
        setContentView(R.layout.activity_edit_critique);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PastWorkActivity.class);
                startActivity(intent);
            }
        });

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