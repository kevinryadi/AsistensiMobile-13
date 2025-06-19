package com.example.tp8;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp8.Database.DatabaseContract;
import com.example.tp8.Database.BookHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity {
    public static final String EXTRA_BOOK = "extra_book";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;
    private BookHelper bookHelper;
    private Book book;
    private EditText etName, etNim;
    private boolean isEdit = false;
    private String initialJudul = "";
    private String initialDeskripsi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> onBackPressed());

        etName = findViewById(R.id.et_name);
        etNim = findViewById(R.id.et_nim);
        Button btnSave = findViewById(R.id.btn_save);
        ImageView btnDelete = findViewById(R.id.btn_delete);

        bookHelper = BookHelper.getInstance(getApplicationContext());
        bookHelper.open();

        book = getIntent().getParcelableExtra(EXTRA_BOOK);

        if (book != null) {
            isEdit = true;
            initialJudul = book.getJudul();
            initialDeskripsi = book.getDeskripsi();
            etName.setText(initialJudul);
            etNim.setText(initialDeskripsi);
        } else {
            book = new Book();
        }

        String actionBarTitle = isEdit ? "Edit Book" : "Add Book";
        String buttonTitle = isEdit ? "Edit" : "Simpan";

        btnSave.setText(buttonTitle);
        btnDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setOnClickListener(view -> saveBook());
        btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Yakin ingin menghapus data ini?")
                    .setPositiveButton("Hapus", (dialog, which) -> deleteBook())
                    .setNegativeButton("Batal", null)
                    .show();
        });
    }

    private void saveBook() {
        String judul = etName.getText().toString().trim();
        String deskripsi = etNim.getText().toString().trim();

        if (judul.isEmpty()) {
            etName.setError("Please fill this field");
            return;
        }

        if (deskripsi.isEmpty()) {
            etNim.setError("Please fill this field");
            return;
        }

        book.setJudul(judul);
        book.setDeskripsi(deskripsi);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_BOOK, book);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.BookColumn.JUDUL, judul);
        values.put(DatabaseContract.BookColumn.DESKRIPSI, deskripsi);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        if (isEdit) {
            values.put(DatabaseContract.BookColumn.UPDATED_AT, currentDate);
            book.setUpdatedAt(currentDate);

            long result = bookHelper.update(String.valueOf(book.getId()), values);
            if (result > 0) {
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        } else {
            values.put(DatabaseContract.BookColumn.CREATED_AT, currentDate);
            book.setCreatedAt(currentDate);

            long result = bookHelper.insert(values);
            if (result > 0) {
                book.setId((int) result);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteBook() {
        if (book != null && book.getId() > 0) {
            long result = bookHelper.deleteById(String.valueOf(book.getId()));
            if (result > 0) {
                setResult(RESULT_DELETE);
                finish();
            } else {
                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid book data", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFormChanged() {
        String currentJudul = etName.getText().toString().trim();
        String currentDeskripsi = etNim.getText().toString().trim();

        if (isEdit) {
            return !currentJudul.equals(initialJudul) || !currentDeskripsi.equals(initialDeskripsi);
        } else {
            return !currentJudul.isEmpty() || !currentDeskripsi.isEmpty();
        }
    }

    @Override
    public void onBackPressed() {
        if (isFormChanged()) {
            new AlertDialog.Builder(this)
                    .setTitle("Keluar tanpa menyimpan?")
                    .setMessage("Yakin ingin keluar? Perubahan tidak akan disimpan.")
                    .setPositiveButton("Ya", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Batal", null)
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Tidak menambahkan data?")
                    .setMessage("Apakah kamu yakin tidak ingin menambahkan data?")
                    .setPositiveButton("Ya", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Batal", null)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookHelper != null) {
            bookHelper.close();
        }
    }
}