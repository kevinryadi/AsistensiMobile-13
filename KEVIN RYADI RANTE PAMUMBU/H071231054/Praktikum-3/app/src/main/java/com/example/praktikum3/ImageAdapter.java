package com.example.praktikum3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context context;
    private List<Feed> imageList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ImageAdapter(Context context, List<Feed> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public ImageAdapter(Context context, List<Feed> imageList, OnItemClickListener listener) {
        this.context = context;
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feed feed = imageList.get(position);
        Object imagePost = feed.getImagePost();
        Boolean imageUri = feed.isImageUri();

        if (imagePost instanceof String) {
            // Jika imagePost adalah String, asumsikan itu adalah URI
            Glide.with(context)
                    .load(imagePost)
                    .into(holder.gridImageView);
            Log.d("ImageAdapter", "Loading image from URI: " + imagePost);

        } else if (imagePost instanceof Integer) {
            // Jika imagePost adalah Integer, asumsikan itu adalah resource ID
            holder.gridImageView.setImageResource((Integer) imagePost);

        } else {
            // Jika tipe data tidak diketahui, atur gambar default atau log error
            Log.e("ImageAdapter", "Tipe data imagePost tidak diketahui");
            holder.gridImageView.setImageResource(R.drawable.ic_launcher_background); // Gambar default
        }

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        }

        Log.d("ImageAdapter", "imagePost type: " + imagePost.getClass().getName());
        if (imagePost instanceof String) {
            Log.d("ImageAdapter", "Loading image from URI: " + imagePost);
        } else if (imagePost instanceof Integer) {
            Log.d("ImageAdapter", "Loading image from resource ID: " + imagePost);
        }

        Glide.with(context)
                .load(imagePost)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Load failed", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        Log.d("Glide", "Image load success");
                        return false;
                    }
                })
                .into(holder.gridImageView);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gridImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridImageView = itemView.findViewById(R.id.gridImageView);
        }
    }
}