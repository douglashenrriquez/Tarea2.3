package com.example.tarea23;

import android.graphics.Bitmap;

public class Photograph {
    private Bitmap image;
    private String description;

    public Photograph(Bitmap image, String description) {
        this.image = image;
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
