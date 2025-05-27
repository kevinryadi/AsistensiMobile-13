package com.example.praktikum3;

public class User {
    private String userId;
    private String username;
    private String name;
    private String bio;
    private String profileImageUrl; // Bisa berupa URL string atau resource ID (String)
    private int postCount;
    private String followerCount;
    private String followingCount;

    public User(String userId, String username, String name, String bio, String profileImageUrl, int postCount, String followerCount, String followingCount) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getPostCount() {
        return postCount;
    }

    public String getFollowerCount() {
        return followerCount;
    }

    public String getFollowingCount() {
        return followingCount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }

    public void setFollowingCount(String followingCount) {
        this.followingCount = followingCount;
    }
}