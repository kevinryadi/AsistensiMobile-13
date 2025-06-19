package com.example.tp8;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.tp8.Database.BookHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvStudents;
    private BookAdapter adapter;
    private BookHelper bookHelper;
    private TextView noData;
    private SearchView searchView;
    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Book List");
        }

        rvStudents = findViewById(R.id.rv_students);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        noData = findViewById(R.id.noData);
        searchView = findViewById(R.id.search);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this);
        rvStudents.setAdapter(adapter);

        bookHelper = BookHelper.getInstance(getApplicationContext());

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        loadBooks();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBooks(newText);
                return true;
            }
        });
    }

    private void loadBooks() {
        new LoadBooksAsync(this, books -> {
            if (books.size() > 0) {
                adapter.setBooks(books);
                noData.setVisibility(View.GONE);
            } else {
                adapter.setBooks(new ArrayList<>());
                noData.setVisibility(View.VISIBLE);
                showToast("No data available");
            }
        }).execute();
    }

    private void searchBooks(String query) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            bookHelper.open();
            Cursor cursor = bookHelper.searchByJudul(query);
            ArrayList<Book> result = MappingHelper.mapCursorToArrayList(cursor);
            cursor.close();
            bookHelper.close();

            handler.post(() -> {
                if (result.size() > 0) {
                    adapter.setBooks(result);
                    noData.setVisibility(View.GONE);
                } else {
                    adapter.setBooks(new ArrayList<>());
                    noData.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == FormActivity.RESULT_ADD) {
            showToast("Student added successfully");
            loadBooks();
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormActivity.RESULT_UPDATE) {
                showToast("Student updated successfully");
                loadBooks();
            } else if (resultCode == FormActivity.RESULT_DELETE) {
                showToast("Student deleted successfully");
                loadBooks();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookHelper != null) {
            bookHelper.close();
        }
    }

    private static class LoadBooksAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadBooksCallback> weakCallback;

        private LoadBooksAsync(Context context, LoadBooksCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    BookHelper bookHelper = BookHelper.getInstance(context);
                    bookHelper.open();
                    Cursor booksCursor = bookHelper.queryAll();
                    ArrayList<Book> books = MappingHelper.mapCursorToArrayList(booksCursor);
                    booksCursor.close();
                    bookHelper.close();

                    handler.post(() -> {
                        LoadBooksCallback callback = weakCallback.get();
                        if (callback != null) {
                            callback.postExecute(books);
                        }
                    });
                }
            });
        }
    }

    interface LoadBooksCallback {
        void postExecute(ArrayList<Book> books);
    }
}