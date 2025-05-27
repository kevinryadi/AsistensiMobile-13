package com.example.tp5;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp5.Adapter.BookAdapter;
import com.example.tp5.Data.DataBook;
import com.example.tp5.Models.Book;
import com.example.tp5.Repository.BookRepository;
import com.example.tp5.databinding.FragmentFavoritesBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private BookAdapter bookAdapter;
    private ArrayList<Book> favoriteBooks = new ArrayList<>();
    private String currentGenre = "All Genres";
    private String currentQuery = "";
    private Runnable runnable;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        ((MainActivity) requireActivity()).showBottomNav();

        favoriteBooks = new ArrayList<>(BookRepository.getFavoriteBooks());


        bookAdapter = new BookAdapter(new ArrayList<>(), this::onBookClicked);
        binding.bookRecycler.setAdapter(bookAdapter);

        if (favoriteBooks.isEmpty()) {
            binding.bookRecycler.setVisibility(View.GONE);
            binding.emptyFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.bookRecycler.setVisibility(View.VISIBLE);
            binding.emptyFavorites.setVisibility(View.GONE);
            bookAdapter.updateBooks(new ArrayList<>(favoriteBooks));
        }

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tampilLoading(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tampilLoading(newText);
                return true;
            }
        });

        setupGenreButtons();

        return view;
    }

    private void onBookClicked(Book book) {
        DetailBookFragment fragment = new DetailBookFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book", book);
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private ArrayList<Book> filter(String query, String genre) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book book : favoriteBooks) {
            boolean matchesQuery = book.getJudul().toLowerCase().contains(query.toLowerCase()) ||
                    book.getPenulis().toLowerCase().contains(query.toLowerCase());
            boolean matchesGenre = genre.equals("All Genres") || book.getGenre().equalsIgnoreCase(genre);
            if (matchesQuery && matchesGenre) {
                filteredList.add(book);
            }
        }
        return filteredList;
    }

    private void tampilLoading(String query) {
        currentQuery = query;
        binding.pb.setVisibility(View.VISIBLE);
        binding.bookRecycler.setVisibility(View.GONE);
        binding.emptyFavorites.setVisibility(View.GONE);

        new Thread(() -> {
            ArrayList<Book> filteredBooks = filter(query, currentGenre);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                if (filteredBooks.isEmpty()) {
                    binding.emptyFavorites.setVisibility(View.VISIBLE);
                    binding.bookRecycler.setVisibility(View.GONE);
                } else {
                    bookAdapter.filterList(filteredBooks);
                    binding.emptyFavorites.setVisibility(View.GONE);
                    binding.bookRecycler.setVisibility(View.VISIBLE);
                }
                binding.pb.setVisibility(View.GONE);
            });
        }).start();
    }

    private void setupGenreButtons() {
        binding.allGenres.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, true);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("All Genres");
        });

        binding.romance.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, true);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Romance");
        });

        binding.fantasy.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, true);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Fantasy");
        });

        binding.scifi.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, true);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Sci-Fi");
        });

        binding.thriller.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, true);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Thriller");
        });

        binding.horror.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, true);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Horror");
        });

        binding.drama.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, true);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Drama");
        });

        binding.history.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, true);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("History");
        });

        binding.comedy.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, true);
            changeButtonStyle(binding.sliceOfLife, false);
            filterBooksByGenre("Comedy");
        });

        binding.sliceOfLife.setOnClickListener(v -> {
            changeButtonStyle(binding.allGenres, false);
            changeButtonStyle(binding.romance, false);
            changeButtonStyle(binding.fantasy, false);
            changeButtonStyle(binding.scifi, false);
            changeButtonStyle(binding.thriller, false);
            changeButtonStyle(binding.horror, false);
            changeButtonStyle(binding.drama, false);
            changeButtonStyle(binding.history, false);
            changeButtonStyle(binding.comedy, false);
            changeButtonStyle(binding.sliceOfLife, true);
            filterBooksByGenre("Slice of Life");
        });
    }

    private void changeButtonStyle(MaterialButton button, boolean isSelected) {
        if (isSelected) {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.genre_button));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.judul));
        } else {
            button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.background));
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.utama));
        }
    }

    private void filterBooksByGenre(String genre) {
        currentGenre = genre;
        tampilLoading(currentQuery);
    }

    private void loadFavoriteBooks() {
        favoriteBooks.addAll(DataBook.books);
        bookAdapter.notifyDataSetChanged();

        if (favoriteBooks.isEmpty()) {
            binding.emptyFavorites.setVisibility(View.VISIBLE);
        } else {
            binding.emptyFavorites.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}