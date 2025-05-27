package com.example.tp5;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.tp5.Data.DataBook;
import com.example.tp5.Models.Book;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp5.Adapter.BookAdapter;
import com.example.tp5.Adapter.CarouselAdapter;
import com.example.tp5.Data.DataCarousel;
import com.example.tp5.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ArrayList<Book> allBooks = new ArrayList<>(DataBook.books);
    private BookAdapter adapter;
    private String currentGenre = "All Genres";
    private String currentQuery = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

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
                tampilLoading(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tampilLoading(newText);
                return true;
            }
        });

        return binding.getRoot();
    }

    private ArrayList<Book> filter(String query, String genre) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book book : allBooks) {
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
        if (binding == null) return;

        currentQuery = query;
        binding.pb.setVisibility(View.VISIBLE);
        binding.bookRecycler.setVisibility(View.GONE);
        binding.emptyGenre.setVisibility(View.GONE);

        new Thread(() -> {
            ArrayList<Book> filteredBooks = filter(query, currentGenre);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                if (binding == null) return;
                if (filteredBooks.isEmpty()) {
                    binding.emptyGenre.setVisibility(View.VISIBLE);
                    binding.bookRecycler.setVisibility(View.GONE);
                } else {
                    adapter.filterList(filteredBooks);
                    binding.emptyGenre.setVisibility(View.GONE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }
}