package com.example.tp8.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "book";

    public static final class BookColumn implements BaseColumns {
        public static final String JUDUL = "judul";
        public static final String DESKRIPSI = "deskripsi";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
    }
}