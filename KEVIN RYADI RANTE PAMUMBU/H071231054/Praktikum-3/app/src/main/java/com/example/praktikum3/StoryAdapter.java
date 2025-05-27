package com.example.praktikum3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    private List<Story> storyList;

    public StoryAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }

    @Override
    public StoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StoryAdapter.ViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.storyImage.setImageResource(story.getImageResId());
        holder.storyName.setText(story.getUsername());
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView storyImage;
        public TextView storyName;

        public ViewHolder(View itemView) {
            super(itemView);
            storyImage = itemView.findViewById(R.id.cIV_profile);
            storyName = itemView.findViewById(R.id.tV_username);
        }
    }
}

