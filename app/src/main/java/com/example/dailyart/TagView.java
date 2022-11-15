package com.example.dailyart;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class TagView extends FrameLayout {

    private GridView artworkGV;
    private TagViewAdapter tva;


    public TagView(Context context) {
        super(context);
        initView();
    }

    public TagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public TagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public TagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void setGalleryAdapter(TagViewAdapter tva){
//        initView();
        this.tva = tva;
        GridView gv = findViewById(R.id.artwork_gallery);
        gv.setAdapter(tva);
        invalidate();
    }

    private void initView() {
        inflate(getContext(), R.layout.tag_view, this);
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        setContentView(R.layout.tag_view); //setContentView(R.layout.activity_past_works);
        return inflater.inflate(R.layout.tag_view, container, false);

    }

}
