package com.example.praktikum3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView cIV_profile;
    private TextView tV_username, countPost, post, countFollower, follower, countFollowing, Following, tvName, bio;
    private RecyclerView rV_feed_fragment, rV_highlight;
    private ImageAdapter imageAdapter;
    private HighlightAdapter highlightAdapter;
    private String userId = "login"; //  Ganti dengan cara Anda mendapatkan user ID yang login

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cIV_profile = view.findViewById(R.id.cIV_profile);
        tV_username = view.findViewById(R.id.tV_username);
        countPost = view.findViewById(R.id.countPost);
        post = view.findViewById(R.id.post);
        countFollower = view.findViewById(R.id.countFollower);
        follower = view.findViewById(R.id.follower);
        countFollowing = view.findViewById(R.id.countFollowing);
        Following = view.findViewById(R.id.Following);
        tvName = view.findViewById(R.id.tV_name);
        bio = view.findViewById(R.id.bio);
        rV_feed_fragment = view.findViewById(R.id.rV_feed_fragment);
        rV_highlight = view.findViewById(R.id.rV_highlight);

        // Tambahkan log untuk userId
        Log.d("ProfileFragment", "userId: " + userId);
        List<Feed> posts = UserStaticData.getPosts(userId);
        for (Feed f : posts) {
            Log.d("ProfileFragment", "imagePost: " + f.getImagePost() + ", isImageUri: " + f.isImageUri());
        }
//        if (posts == null) posts = new ArrayList<>(); // Tambahkan ini jika tidak ada data
//        // Set LayoutManager dan Adapter untuk RecyclerView
//        rV_feed_fragment.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        imageAdapter = new ImageAdapter(getContext(), posts);
//        rV_feed_fragment.setAdapter(imageAdapter); // Pastikan adapter terpasang sebelum layout di-render
//        imageAdapter.notifyDataSetChanged(); // Tambahkan ini jika ada perubahan data
//        imageAdapter.setOnItemClickListener(position -> {
//            Intent intent = new Intent(getActivity(), FeedDetailActivity.class);
//            startActivity(intent);
//        });

        refreshPostList();
        User user = UserStaticData.getUser(userId);
        if (user != null) {
            tV_username.setText(user.getUsername());
            tvName.setText(user.getName());
            bio.setText(user.getBio());
            countPost.setText(String.valueOf(user.getPostCount()));
            countFollower.setText(user.getFollowerCount());
            countFollowing.setText(user.getFollowingCount());
            try {
                int profileImageResource = Integer.parseInt(user.getProfileImageUrl());
                cIV_profile.setImageResource(profileImageResource);
            } catch (NumberFormatException e) {
                // Handle jika profileImageUrl bukan resource ID yang valid
                cIV_profile.setImageResource(R.drawable.ic_launcher_background); // Set default
            }
        } else {
            Log.e("UserProfile", "Data user tidak ditemukan untuk ID: " + userId);
        }


        List<Integer> highlights = UserStaticData.getHighlights(userId);
        if (highlights != null && !highlights.isEmpty()) {
            rV_highlight.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            highlightAdapter = new HighlightAdapter(getContext(), highlights);
            rV_highlight.setAdapter(highlightAdapter);
        } else {
            Log.w("UserProfile", "Tidak ada highlight atau daftar highlight kosong untuk user ID: " + userId);
            // Handle jika tidak ada highlight untuk user ini
        }

    }
    private void refreshPostList() {
        List<Feed> posts = UserStaticData.getPosts(userId);
        if (posts == null) posts = new ArrayList<>();
        imageAdapter = new ImageAdapter(getContext(), posts);
        rV_feed_fragment.setAdapter(imageAdapter);
        List<Feed> finalPosts1 = posts;
        imageAdapter.setOnItemClickListener(position -> {
            Feed selectedFeed = finalPosts1.get(position);
            Intent intent = new Intent(getContext(), FeedDetailActivity.class);
            intent.putExtra("username", selectedFeed.getUsername());
            intent.putExtra("caption", selectedFeed.getCaption());
            intent.putExtra("imageProfile", selectedFeed.getImageProfile());

            if (selectedFeed.getImagePost() instanceof String) {
                intent.putExtra("imagePost", (String) selectedFeed.getImagePost());
            } else if (selectedFeed.getImagePost() instanceof Integer) {
                intent.putExtra("imagePost", (Integer) selectedFeed.getImagePost());
            }

            startActivity(intent);
        });

        imageAdapter.notifyDataSetChanged();
        Log.d("ProfileFragment", "refreshPostList dipanggil, total post: " + posts.size());
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPostList(); // setiap kali fragment ini muncul, kita segarkan data
    }



//    private void loadUserProfile(String userId) {
//        User user = UserStaticData.getUser(userId);
//        if (user != null) {
//            tV_username.setText(user.getUsername());
//            tvName.setText(user.getName());
//            bio.setText(user.getBio());
//            countPost.setText(String.valueOf(user.getPostCount()));
//            countFollower.setText(user.getFollowerCount());
//            countFollowing.setText(user.getFollowingCount());
//            try {
//                int profileImageResource = Integer.parseInt(user.getProfileImageUrl());
//                cIV_profile.setImageResource(profileImageResource);
//            } catch (NumberFormatException e) {
//                // Handle jika profileImageUrl bukan resource ID yang valid
//                cIV_profile.setImageResource(R.drawable.ic_launcher_background); // Set default
//            }
//        } else {
//            Log.e("UserProfile", "Data user tidak ditemukan untuk ID: " + userId);
//        }
//    }
//    private void loadUserPosts(String userId) {
//        List<Feed> posts = UserStaticData.getPosts(userId);
//        if (posts == null) posts = new ArrayList<>(); // Tambahkan ini jika tidak ada data
//        // Set LayoutManager dan Adapter untuk RecyclerView
//        rV_feed_fragment.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        imageAdapter = new ImageAdapter(getContext(), posts);
//        rV_feed_fragment.setAdapter(imageAdapter); // Pastikan adapter terpasang sebelum layout di-render
//        imageAdapter.notifyDataSetChanged(); // Tambahkan ini jika ada perubahan data
//        List<Feed> finalPosts = posts;
//        imageAdapter.setOnItemClickListener(position -> {
//            Intent intent = new Intent(getActivity(), FeedDetailActivity.class);
//            startActivity(intent);
//        });
//
//        Log.d("ProfileFragment", "Loading posts for userId: " + userId + ", post count: " + posts.size());
//    }

}