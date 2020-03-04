package com.example.cs4720.my_application;

import java.io.Serializable;
import java.util.Date;

public class BucketItem implements Comparable<BucketItem>, Serializable{

    private String title;
    private boolean checked;
    private String description;
    private Date date;
    private double latitude;
    private double longitude;

    public BucketItem(String title, boolean checked, String description, Date date, double latitude, double longitude) {
        this.title = title.trim();
        this.checked = checked;
        this.description = description.trim();
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double d) { this.latitude = d; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double d) { this.longitude = d; }


    /** compareTo() to determine which items come before others in an ArrayList**/
    @Override
    public int compareTo(BucketItem that) {
        if (this.checked && !that.isChecked()) {
            return 1;
        }
        else if (!this.checked && that.isChecked()) {
            return -1;
        }
        else {
            return this.date.compareTo(that.getDate());
        }
    }
}
