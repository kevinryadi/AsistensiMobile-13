package com.example.tp4.Data;

import com.example.tp4.Models.Carousel;
import com.example.tp4.R;

import java.util.ArrayList;

public class DataCarousel {
    public static ArrayList<Carousel> carousels = generateDummyCarousels();

    private static ArrayList<Carousel> generateDummyCarousels() {
        ArrayList<Carousel> carousels = new ArrayList<>();
        carousels.add(new Carousel(R.drawable.carousel1));
        carousels.add(new Carousel(R.drawable.carousel1));
        carousels.add(new Carousel(R.drawable.carousel1));
        return carousels;
    }
}
