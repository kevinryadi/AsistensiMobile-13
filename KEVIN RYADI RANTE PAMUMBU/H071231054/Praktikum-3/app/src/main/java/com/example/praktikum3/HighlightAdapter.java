package com.example.praktikum3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {
    private Context context;
    private List<Integer> highlightList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HighlightAdapter(Context context, List<Integer> highlightList) {
        this.context = context;
        this.highlightList = highlightList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_highlight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int highlightImage = highlightList.get(position);
        holder.highlightImageView.setImageResource(highlightImage);
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HighlightDetailActivity.class);
            intent.putExtra("highlight_image", highlightImage);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return highlightList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView highlightImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            highlightImageView = itemView.findViewById(R.id.iV_highlight);
        }
    }
}
