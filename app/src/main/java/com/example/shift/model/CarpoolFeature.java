package com.example.shift.model;

import android.view.View;

public class CarpoolFeature {
    private String title;
    private String description;
    private int iconResId;
    private String iconContentDescription;
    private View.OnClickListener onClickListener;

    public CarpoolFeature(String title, String description, int iconResId, String iconContentDescription, View.OnClickListener onClickListener) {
        this.title = title;
        this.description = description;
        this.iconResId = iconResId;
        this.iconContentDescription = iconContentDescription;
        this.onClickListener = onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getIconContentDescription() {
        return iconContentDescription;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}