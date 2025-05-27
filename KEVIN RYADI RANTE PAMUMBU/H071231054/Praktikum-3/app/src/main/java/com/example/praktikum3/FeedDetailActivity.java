package com.example.praktikum3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedDetailActivity extends AppCompatActivity {
    private CircleImageView imageProfile;
    private ImageView imagePost;
    private TextView username, like, comment, share, captionUsername, caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.item_feed); // gunakan layout item_feed

        imageProfile = findViewById(R.id.cIV_profile);
        imagePost = findViewById(R.id.iV_postingan);
        username = findViewById(R.id.tV_username);
        like = findViewById(R.id.tV_like);
        comment = findViewById(R.id.tV_comment);
        share = findViewById(R.id.tV_share);
        captionUsername = findViewById(R.id.tV_caption_username);
        caption = findViewById(R.id.tV_caption);

// Ambil data dari intent
        Intent intent = getIntent();
        String usernameText = intent.getStringExtra("username");
        String captionText = intent.getStringExtra("caption");
        int imageProfileRes = intent.getIntExtra("imageProfile", R.drawable.profile1);
        Object imagePostObj = intent.getSerializableExtra("imagePost");

        username.setText(usernameText);
        captionUsername.setText(usernameText);
        caption.setText(captionText);
        imageProfile.setImageResource(imageProfileRes);

        if (imagePostObj instanceof String) {
            Glide.with(this).load((String) imagePostObj).into(imagePost);
        } else if (imagePostObj instanceof Integer) {
            imagePost.setImageResource((int) imagePostObj);
        }
    }
}