package com.example.tp4.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4.Models.Carousel;
import com.example.tp4.R;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>  {
    private List<Carousel> carouselList;

    public CarouselAdapter(List<Carousel> carouselList) {
        this.carouselList = carouselList;
    }

    @NonNull
    @Override
    public CarouselAdapter.CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselAdapter.CarouselViewHolder holder, int position) {
        Carousel carousel = carouselList.get(position);
        holder.carouselImage.setImageResource(carousel.getCarouselImage());
    }

    @Override
    public int getItemCount() {
        return carouselList.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView carouselImage;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselImage = itemView.findViewById(R.id.carouselImage);
        }
    }
}
