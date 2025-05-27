// FeedFragment.java
package com.example.praktikum3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    private RecyclerView rV_stories;
    private RecyclerView rV_feed;
    private List<Story> storyList;
    private List<Feed> feedList;
    private StoryAdapter storyAdapter;
    private FeedAdapter feedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rV_feed = view.findViewById(R.id.rV_feed);
        feedList = new ArrayList<>();
        // Dapatkan semua postingan dari semua user
        for (String userId : UserStaticData.userPosts.keySet()) {
            List<Feed> posts = UserStaticData.userPosts.get(userId);
            if (posts != null) {
                feedList.addAll(0, posts);
            }
        }
        feedAdapter = new FeedAdapter(getContext(), feedList);
        rV_feed.setLayoutManager(new LinearLayoutManager(getContext()));
        rV_feed.setAdapter(feedAdapter);

        rV_stories = view.findViewById(R.id.rV_stories);
        storyList = new ArrayList<>();
        storyList.add(new Story(R.drawable.gfriend1, "gfriendofficial"));
        storyList.add(new Story(R.drawable.viviz1, "vivizofficial"));
        storyList.add(new Story(R.drawable.profile1, "kvnryadi"));

        storyAdapter = new StoryAdapter(storyList);
        rV_stories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rV_stories.setAdapter(storyAdapter);
    }

}