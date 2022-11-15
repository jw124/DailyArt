package com.example.dailyart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;
import java.util.List;

public class TagViewAdapter extends ArrayAdapter<ArtworkModel> {

    private Context mcontext;

    public TagViewAdapter(@NonNull Context context, @NonNull ArtworkModel[] objects) {
        super(context, 0, objects);
        this.mcontext = context;
    }

    public TagViewAdapter(@NonNull Context context,  @NonNull List<ArtworkModel> objects) {
        super(context, 0, objects);
        this.mcontext = context;
    }

    public TagViewAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<ArtworkModel> objects) {
        super(context, resource, textViewResourceId, objects);
        this.mcontext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.tagged_image, parent,false);
        }

        ArtworkModel am = getItem(position);
        TextView tv = listItemView.findViewById(R.id.artwork_description);
        ImageView iv = listItemView.findViewById(R.id.tagged_image);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditCritiqueActivity.class);
                String strName = null;
                i.putExtra("IMAGE_FILEPATH", am.getImagePath());
                i.putExtra("CRITIQUE_FILEPATH", am.getCritiquePath());
                //start a new activity to open the critique page
                mcontext.startActivity(i);
            }
        });

        tv.setText(am.getCritique());
        iv.setImageURI(am.getImagePath());

        return listItemView;

    }


}
