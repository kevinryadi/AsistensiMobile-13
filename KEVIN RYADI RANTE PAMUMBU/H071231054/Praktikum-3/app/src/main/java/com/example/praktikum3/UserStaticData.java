package com.example.praktikum3;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStaticData {
    private static final Map<String, User> users = new HashMap<>();
    static final Map<String, List<Feed>> userPosts = new HashMap<>();
    private static final Map<String, List<Integer>> userHighlights = new HashMap<>();



    static {
        // Data untuk user dengan ID "user1"
        // ...
        users.put("login", new User(
                "login",
                "kvnryadi",
                "Kevin Ryadi",
                "Jeremiah 17:7",
                String.valueOf(R.drawable.profile1),
                2,
                "980",
                "826"
        ));
        List<Feed> postsLogin = new ArrayList<>();
        postsLogin.add(new Feed(R.drawable.profile1, R.drawable.profile1, "kvnryadi", "500", "0", "0", "login", "kvnryadi", "halooo", "", false)); // imagePost berupa Integer (resource ID)
        postsLogin.add(new Feed(R.drawable.profile1, R.drawable.feed1, "kvnryadi", "500", "0", "0", "login", "kvnryadi", "Holaaa", "", false)); // imagePost berupa Integer (resource ID)

        Log.d("UserStaticData", "User1 Posts: " + postsLogin);
        userPosts.put("login", postsLogin);

        List<Integer> highlightsLogin = new ArrayList<>();
        highlightsLogin.add(R.drawable.profile1);
        highlightsLogin.add(R.drawable.feed1);
        userHighlights.put("login", highlightsLogin);

        users.put("user1", new User(
                "user1",
                "gfriendofficial",
                "Gfriend",
                "new album already out",
                String.valueOf(R.drawable.gfriend1),
                2,
                "3,4JT",
                "8"
        ));
        List<Feed> postsUser1 = new ArrayList<>();
        postsUser1.add(new Feed(R.drawable.gfriend1, R.drawable.gfriend1, "gfriendofficial", "264rb", "19", "1.512", "user1", "gfriendofficial", "buddy <3", "", false)); // imagePost berupa Integer (resource ID)
        postsUser1.add(new Feed(R.drawable.gfriend2, R.drawable.gfriend2, "gfriendofficial", "77,3rb", "356", "285", "user1", "gfriendofficial", "together", "", false)); // imagePost berupa Integer (resource ID)
        Log.d("UserStaticData", "User1 Posts: " + postsUser1);
        userPosts.put("user1", postsUser1);

        List<Integer> highlightsUser1 = new ArrayList<>();
        highlightsUser1.add(R.drawable.gfriend1);
        highlightsUser1.add(R.drawable.gfriend2);
        userHighlights.put("user1", highlightsUser1);

        // Data untuk user dengan ID "user2"
        users.put("user2", new User(
                "user2",
                "vivizofficial",
                "Viviz",
                "new album already out",
                String.valueOf(R.drawable.viviz1),
                2,
                "1,4JT",
                "4"
        ));
        List<Feed> postsUser2 = new ArrayList<>();
        postsUser2.add(new Feed(R.drawable.viviz1, R.drawable.viviz1, "vivizofficial", "3,4JT", "21,7rb", "13,2rb", "user2", "vivizofficial", "holaaa", "", false)); // imagePost berupa Integer (resource ID)
        postsUser2.add(new Feed(R.drawable.viviz2, R.drawable.viviz2, "vivizofficial", "1,2JT", "8.430", "1.375", "user2", "vivizofficial", "nav<3", "", false)); // imagePost berupa Integer (resource ID)
//        postsUser2.add(new Feed(R.drawable.realmadrid, R.drawable.realmadrid, "cristiano", "6,3JT", "64,4rb", "68,3rb", "user2", "cristiano", "Hala Madrid", "", false)); // imagePost berupa Integer (resource ID)
        Log.d("UserStaticData", "User2 Posts: " + postsUser2);
        userPosts.put("user2", postsUser2);
        List<Integer> highlightsUser2 = new ArrayList<>();
        highlightsUser2.add(R.drawable.viviz1);
        highlightsUser2.add(R.drawable.viviz2);
        userHighlights.put("user2", highlightsUser2);


        Log.d("UserStaticData", "Initial userPosts: " + userPosts);


        // Tambahkan data pengguna statis lainnya di sini jika perlu
    }

    public static User getUser(String userId) {




        return users.get(userId);
    }
    public static List<Feed> getPosts(String userId) {

        return userPosts.get(userId);
    }
    public static List<Integer> getHighlights(String userId) {

        return userHighlights.get(userId);
    }

}