package com.example.praktikum3;

public class Feed {
    private int imageProfile;
    private Object imagePost;
    private String username;
    private String like;
    private String comment;
    private String userId;
    private String share;
    private String caption_username;
    private String caption;
    private String imageUri;
    private boolean isImageUri;
    public Feed(int imageProfile, Object imagePost, String username, String like, String comment, String share, String userId , String caption_username, String caption, String imageUri, boolean isImageUri) {
        this.imageProfile = imageProfile;
        this.imagePost = imagePost;
        this.username = username;
        this.like = like;
        this.comment = comment;
        this.share = share;
        this.userId = userId;
        this.caption_username = caption_username;
        this.caption = caption;
        this.imageUri = imageUri;
        this.isImageUri = isImageUri;
    }
    public String getImageUri() {
        return imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public int getImageProfile() {
        return imageProfile;
    }
    public Object getImagePost() {
        return imagePost;
    }
    public String getUsername() {
        return username;
    }
    public String getLike() {
        return like;
    }
    public String getComment() {
        return comment;
    }
    public String getShare() {
        return share;
    }
    public String getUserId() {
        return userId;
    }
    public void setImageProfile(int imageProfile) {
        this.imageProfile = imageProfile;
    }
    public void setImagePost(Object imagePost) {
        this.imagePost = imagePost;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    public String getCaption_username() {
        return caption_username;
    }

    public void setCaption_username(String caption_username) {
        this.caption_username = caption_username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    public boolean isImageUri() {
        return isImageUri;
    }

    public void setImageUri(boolean imageUri) {
        isImageUri = imageUri;
    }
}

