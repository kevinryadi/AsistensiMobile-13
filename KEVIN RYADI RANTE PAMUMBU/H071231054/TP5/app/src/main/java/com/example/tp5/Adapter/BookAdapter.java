package com.example.tp5.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp5.Models.Book;
import com.example.tp5.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private ArrayList<Book> books;
    private OnBookClickListener listener;

    public BookAdapter(ArrayList<Book> books, OnBookClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.cover.setImageResource(book.getCover());
        holder.judul.setText(book.getJudul());
        holder.penulis.setText(book.getPenulis());

        if (book.getCoverUri() != null) {
            holder.cover.setImageURI(book.getCoverUri());
        } else if (book.getCover() != 0) {
            holder.cover.setImageResource(book.getCover());
        }

        holder.itemView.setOnClickListener(v -> listener.onBookClick(book));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void filterList(ArrayList<Book> filteredList) {
        books = filteredList;
        notifyDataSetChanged();
    }

    public void updateBooks(ArrayList<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView judul, penulis;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            judul = itemView.findViewById(R.id.judul);
            penulis = itemView.findViewById(R.id.penulis);
        }
    }

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }
}
