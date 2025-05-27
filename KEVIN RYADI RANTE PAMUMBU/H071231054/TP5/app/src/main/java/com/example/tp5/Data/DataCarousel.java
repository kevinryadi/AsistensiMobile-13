package com.example.tp5.Data;

import com.example.tp5.Models.Carousel;
import com.example.tp5.R;

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