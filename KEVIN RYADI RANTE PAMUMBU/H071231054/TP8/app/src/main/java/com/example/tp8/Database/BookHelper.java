package com.example.tp8.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BookHelper {
    public static String TABLE_NAME = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static volatile BookHelper INSTANCE;

    private BookHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static BookHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BookHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public Cursor searchByJudul(String judul) {
        return sqLiteDatabase.query(
                TABLE_NAME,
                null,
                "judul LIKE ?",
                new String[]{"%" + judul + "%"},
                null,
                null,
                null
        );
    }

    public Cursor queryAll() {
        return sqLiteDatabase.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.BookColumn._ID + " ASC"
        );
    }

    public Cursor queryById(String id) {
        return sqLiteDatabase.query(
                TABLE_NAME,
                null,
                DatabaseContract.BookColumn._ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }

    public long insert(ContentValues values) {
        return sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public int update(String id, ContentValues values) {
        return sqLiteDatabase.update(TABLE_NAME, values,
                DatabaseContract.BookColumn._ID + " = ?",
                new String[]{id});
    }

    public int deleteById(String id) {
        return sqLiteDatabase.delete(TABLE_NAME,
                DatabaseContract.BookColumn._ID + " = ?",
                new String[]{id});
    }
}