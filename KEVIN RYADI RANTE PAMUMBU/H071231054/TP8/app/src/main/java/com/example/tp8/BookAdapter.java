package com.example.tp8;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private final ArrayList<Book> books = new ArrayList<>();
    private final Activity activity;

    public BookAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books.clear();
        if (books.size() > 0) {
            this.books.addAll(books);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName, tvNim, tvTime;
        final CardView cardView;

        BookViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvNim = itemView.findViewById(R.id.tv_item_nim);
            tvTime = itemView.findViewById(R.id.tv_item_timestamp);
            cardView = itemView.findViewById(R.id.card_view);
        }

        void bind(Book book) {
            tvName.setText(book.getJudul());
            tvNim.setText(book.getDeskripsi());

            if (book.getUpdatedAt() != null) {
                tvTime.setText("Updated at " + book.getUpdatedAt());
            } else if (book.getCreatedAt() != null) {
                tvTime.setText("Created at " + book.getCreatedAt());
            } else {
                tvTime.setText(book.getDeskripsi());
            }

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_BOOK, book);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            });
        }
    }
}