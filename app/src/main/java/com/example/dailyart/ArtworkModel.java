package com.example.dailyart;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

public class ArtworkModel {

    private String description;
    private String imagePath;
    private String critique;
    private ArrayList<String> tags;
    private Date uploadDate;
    private String critiquePath;

    public ArtworkModel(String description,
                        String imagePath,
                        String critique,
                        ArrayList<String> tags,
                        Date uploadDate,
                        String critiquePath){
        this.description = description;
        this.imagePath = imagePath;
        this.critique = critique;
        this.tags = tags;
        this.uploadDate = uploadDate;
        this.critiquePath = critiquePath;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getCritiquePath() {
        return Uri.parse(critiquePath);
    }

    public String getCritiquePathAsString() {
        return critiquePath;
    }

    public Uri getImagePath() {
        return Uri.parse(imagePath);
    }

    public String getImagePathAsString() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCritique() {
        return critique;
    }

    public void setCritique(String critique) {
        this.critique = critique;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags.addAll(tags);
    }
}
