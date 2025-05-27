package com.example.praktikum3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity; // Import FragmentActivity
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{
    private List<Feed> feedList;
    private Context context;
    public FeedAdapter(Context context, List<Feed> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        Feed feed = feedList.get(position);
        holder.imageProfile.setImageResource(feed.getImageProfile());
//        holder.imageView.setImageResource(feed.getImagePost());
        holder.username.setText(feed.getUsername());
        holder.like.setText(feed.getLike());
        holder.comment.setText(feed.getComment());
        holder.share.setText(feed.getShare());
        holder.caption_username.setText(feed.getCaption_username());
        holder.caption.setText(feed.getCaption());

        Log.d("FeedAdapter", "imagePost: " + feed.getImagePost() + ", isImageUri: " + feed.isImageUri());

        if (feed.getImagePost() instanceof String) {
            // Jika imagePost adalah String, asumsikan itu adalah URI
            Glide.with(context)
                    .load(feed.getImagePost())
                    .into(holder.imageView);
        } else if (feed.getImagePost() instanceof Integer) {
            // Jika imagePost adalah Integer, asumsikan itu adalah resource ID
            holder.imageView.setImageResource((Integer) feed.getImagePost());
        } else {
            // Jika tipe data tidak diketahui, atur gambar default atau log error
            Log.e("FeedAdapter", "Tipe data imagePost tidak diketahui");
            holder.imageView.setImageResource(R.drawable.ic_launcher_background); // Gambar default
        }

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Periksa apakah userId sama dengan userId yang login
                if (feed.getUserId().equals("login")) {
                    // Navigasi ke ProfileFragment
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("login", feed.getUserId());
                    profileFragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, profileFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    // Navigasi ke UserProfile Activity
                    Intent intent = new Intent(context, UserProfile.class);
                    intent.putExtra("userId", feed.getUserId());
                    context.startActivity(intent);
                }
            }
        });

        holder.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feed.getUserId().equals("login")) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("login", feed.getUserId());
                    profileFragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, profileFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Intent intent = new Intent(context, UserProfile.class);
                    intent.putExtra("userId", feed.getUserId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageProfile;
        public ImageView imageView;
        public TextView username, like, comment, share, caption_username, caption;

        public ViewHolder(View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.cIV_profile);
            imageView = itemView.findViewById(R.id.iV_postingan);
            username = itemView.findViewById(R.id.tV_username);
            like = itemView.findViewById(R.id.tV_like);
            comment = itemView.findViewById(R.id.tV_comment);
            share = itemView.findViewById(R.id.tV_share);
            caption_username = itemView.findViewById(R.id.tV_caption_username);
            caption = itemView.findViewById(R.id.tV_caption);
        }
    }
}
