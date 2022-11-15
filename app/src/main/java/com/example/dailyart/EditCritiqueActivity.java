package com.example.dailyart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class EditCritiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_critique);

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
}