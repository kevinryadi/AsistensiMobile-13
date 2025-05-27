package com.example.praktikum3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    private CircleImageView cIV_profile;
    private String userId;
    private TextView tV_username, countPost, post, countFollower, follower, countFollowing, Following, tvName, bio;
    private RecyclerView rV_feed, rV_highlight;
    private List<Integer> userPosts = new ArrayList<>();
    private List<Integer> highlightStories = new ArrayList<>();
    private HighlightAdapter highlightAdapter;
    private ImageAdapter imageAdapter;
    private ImageView iV_back;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cIV_profile = findViewById(R.id.cIV_profile);
        tV_username = findViewById(R.id.tV_username);
        countPost = findViewById(R.id.countPost);
        post = findViewById(R.id.post);
        countFollower = findViewById(R.id.countFollower);
        follower = findViewById(R.id.follower);
        countFollowing = findViewById(R.id.countFollowing);
        Following = findViewById(R.id.Following);
        tvName = findViewById(R.id.tV_name);
        bio = findViewById(R.id.bio);
        rV_feed = findViewById(R.id.rV_feed);
        rV_highlight = findViewById(R.id.rV_highlight);
        iV_back = findViewById(R.id.iV_back);

        iV_back.setOnClickListener(v -> {
            onBackPressed();
        });

        userId = getIntent().getStringExtra("userId");
        if (userId != null) {
            tV_username.setText(userId);
            loadUserProfile(userId);
            loadUserPosts(userId);
            loadHighlightStories(userId);
        } else {
            // Handle jika userId tidak ada
        }
    }

    private void loadUserProfile(String userId) {
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
    }

    private void loadUserPosts(String userId) {
        List<Feed> posts = UserStaticData.getPosts(userId);
        if (posts != null && !posts.isEmpty()) {
            Log.d("UserProfile", "Jumlah postingan untuk " + userId + ": " + posts.size());
            rV_feed.setLayoutManager(new GridLayoutManager(this, 3));
            imageAdapter = new ImageAdapter(this, posts);
            rV_feed.setAdapter(imageAdapter);

            imageAdapter.setOnItemClickListener(position -> {
                Feed selectedFeed = UserStaticData.getPosts(userId).get(position);
                Intent intent = new Intent(UserProfile.this, FeedDetailActivity.class);
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



        } else {
            Log.w("UserProfile", "Tidak ada postingan atau daftar postingan kosong untuk user ID: " + userId);
            // Tampilkan pesan atau UI yang sesuai jika tidak ada postingan
        }
    }
    private void loadHighlightStories(String userId) {
        List<Integer> highlights = UserStaticData.getHighlights(userId);
        if (highlights != null && !highlights.isEmpty()) {
            rV_highlight.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            highlightAdapter = new HighlightAdapter(this, highlights);
            rV_highlight.setAdapter(highlightAdapter);

            highlightAdapter.setOnItemClickListener(position -> {
                // Pindah ke halaman detail story
                Intent intent = new Intent(this, StoryDetailActivity.class);
                // Anda bisa mengirimkan data highlight jika perlu
                startActivity(intent);
            });
        } else {
            Log.w("UserProfile", "Tidak ada highlight atau daftar highlight kosong untuk user ID: " + userId);
            // Handle jika tidak ada highlight untuk user ini
        }
    }
}