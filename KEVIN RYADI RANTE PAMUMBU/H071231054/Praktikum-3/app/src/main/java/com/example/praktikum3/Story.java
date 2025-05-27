package com.example.praktikum3;

public class Story {
    private int imageResId;
    private String username;

    public Story(int imageResId, String username) {
        this.imageResId = imageResId;
        this.username = username;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getUsername() {
        return username;
    }
}
