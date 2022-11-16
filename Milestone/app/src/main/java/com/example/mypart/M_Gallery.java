package com.example.mypart;
//

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
//

public class M_Gallery extends AppCompatActivity {
    GridView gridView;
    int[] images= {R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5,
            R.drawable.p10, R.drawable.p9, R.drawable.p8, R.drawable.p7,
            R.drawable.p6, R.drawable.p5, R.drawable.p11, R.drawable.p2,
            R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p2 };
    String[] critiques={"test1","test2","test3","test4","test5","test6","test7","test8","test9",
            "test10","test11","test12","test13","test14","test15","test16","test17","test18","test19","test20"};

    private ProgressBar mypb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView=findViewById(R.id.GridView);


        CustomAdapter customAdapter=new CustomAdapter(critiques,images,this);

        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedName = critiques[i];
            int selectedImage = images[i];
            startActivity(new Intent(M_Gallery.this, detail.class).putExtra("name", selectedName).putExtra("image", selectedImage));
        });

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
















