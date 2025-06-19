package com.example.tp8.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookdb.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BOOK = "CREATE TABLE " + DatabaseContract.TABLE_NAME + " (" +
                DatabaseContract.BookColumn._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.BookColumn.JUDUL + " TEXT NOT NULL, " +
                DatabaseContract.BookColumn.DESKRIPSI + " TEXT NOT NULL, " +
                DatabaseContract.BookColumn.CREATED_AT + " TEXT, " +
                DatabaseContract.BookColumn.UPDATED_AT + " TEXT" +
                ")";
        db.execSQL(CREATE_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Jika nanti ada perubahan versi database, misalnya penambahan kolom
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}