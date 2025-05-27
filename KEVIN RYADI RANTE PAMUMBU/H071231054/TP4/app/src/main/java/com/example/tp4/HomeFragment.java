package com.example.tp4;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.tp4.Data.DataBook;
import com.example.tp4.Models.Book;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp4.Adapter.BookAdapter;
import com.example.tp4.Adapter.CarouselAdapter;
import com.example.tp4.Data.DataCarousel;
import com.example.tp4.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ArrayList<Book> allBooks = new ArrayList<>(DataBook.books);
    private BookAdapter adapter;  // Adapter untuk RecyclerView buku

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) requireActivity()).showBottomNav();
        allBooks = new ArrayList<>(DataBook.books);

        adapter = new BookAdapter(allBooks, book -> {
            DetailBookFragment fragment = new DetailBookFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("book", book);
            fragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        binding.bookRecycler.setAdapter(adapter);

        setupGenreButtons();

        binding.carouselRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        CarouselAdapter carouselAdapter = new CarouselAdapter(DataCarousel.carousels);
        binding.carouselRecycler.setAdapter(carouselAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.carouselRecycler);

        runnable = new Runnable() {
            int position = 0;

            @Override
            public void run() {
                if (position >= DataCarousel.carousels.size()) {
                    position = 0;
                    binding.carouselRecycler.scrollToPosition(position);
                } else {
                    binding.carouselRecycler.smoothScrollToPosition(position);
                    position++;
                }
                handler.postDelayed(this, 4000);
            }
        };
        handler.postDelayed(runnable, 4000);

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


        return view;
    }

    private void filter(String text) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getJudul().toLowerCase().contains(text.toLowerCase()) ||
                    book.getPenulis().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(book);
            }
        }

        adapter.filterList(filteredList);

        if (filteredList.isEmpty()) {
            binding.emptyGenre.setVisibility(View.VISIBLE);
        } else {
            binding.emptyGenre.setVisibility(View.GONE);
        }
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
        ArrayList<Book> filteredBooks;

        if (genre.equals("All Genres")) {
            filteredBooks = new ArrayList<>(allBooks);
        } else {
            filteredBooks = new ArrayList<>();
            for (Book book : allBooks) {
                if (book.getGenre().equals(genre)) {
                    filteredBooks.add(book);
                }
            }
        }

        adapter.updateBooks(filteredBooks);

        if (filteredBooks.isEmpty()) {
            binding.emptyGenre.setVisibility(View.VISIBLE);
        } else {
            binding.emptyGenre.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}