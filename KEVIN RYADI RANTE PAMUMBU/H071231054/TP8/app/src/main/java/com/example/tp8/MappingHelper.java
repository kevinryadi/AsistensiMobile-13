package com.example.tp8;

import com.example.tp8.Database.DatabaseContract;

import android.database.Cursor;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Book> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Book> bookList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                book.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.BookColumn.JUDUL)));
                book.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.BookColumn.DESKRIPSI)));
                book.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.BookColumn.CREATED_AT)));
                book.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.BookColumn.UPDATED_AT)));
                bookList.add(book);
            }
            cursor.close();
        }
        return bookList;
    }
}
