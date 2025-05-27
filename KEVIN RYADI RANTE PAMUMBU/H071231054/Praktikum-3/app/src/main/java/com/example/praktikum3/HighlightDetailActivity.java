package com.example.praktikum3;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HighlightDetailActivity extends AppCompatActivity {

    private ImageView highlightImageView;
    private int imageResId; // Gambar dari highlight

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_highlight_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        highlightImageView = findViewById(R.id.highlightImageView);

        imageResId = getIntent().getIntExtra("highlight_image", R.drawable.ic_launcher_background);
        highlightImageView.setImageResource(imageResId);
    }
}