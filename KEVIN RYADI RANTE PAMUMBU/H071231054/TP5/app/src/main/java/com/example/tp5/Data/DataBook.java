package com.example.tp5.Data;

import com.example.tp5.Models.Book;
import com.example.tp5.R;

import java.util.ArrayList;

public class DataBook {
    public static ArrayList<Book> books = generateDummyBooks();

    private static ArrayList<Book> generateDummyBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(R.drawable.laut_bercerita,
                "Laut Bercerita",
                "Leila S. Chudori",
                "Kepustakaan Populer Gramedia",
                2017,
                "9786024246945",
                "Sebuah kisah kehilangan, perlawanan, dan harapan.",
                "Drama",
                "Indonesia",
                "Laut Bercerita mengisahkan tentang Biru Laut, seorang aktivis yang menghilang pada masa penculikan mahasiswa 1998. Novel ini menyentuh tema keluarga, perjuangan, dan kekuasaan.",
                379,
                false
        ));

        books.add(new Book(R.drawable.bumi,
                "Bumi", "Tere Liye", "Gramedia Pustaka Utama", 2014,
                "9786020302032", "Petualangan remaja dengan kekuatan luar biasa.",
                "Fantasy", "Indonesia",
                "Raib, Seli, dan Ali menjelajahi dunia paralel penuh misteri dan teknologi.",
                440, false));

        books.add(new Book(R.drawable.perahu_kertas,
                "Perahu Kertas", "Dee Lestari", "Bentang Pustaka", 2009,
                "9786028811147", "Kisah cinta dan pencarian jati diri.",
                "Romance", "Indonesia",
                "Kugy dan Keenan harus memilih antara mimpi dan cinta.",
                444, false));

        books.add(new Book(R.drawable.dilan1990,
                "Dilan 1990", "Pidi Baiq", "Pastel Books", 2014,
                "9786027870411", "Cinta SMA antara Milea dan Dilan.",
                "Romance", "Indonesia",
                "Dilan, murid nakal yang romantis, mencuri hati Milea.",
                330, false));

        books.add(new Book(R.drawable.harry_potter,
                "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Bloomsbury", 1997,
                "9780747532699", "Bocah penyihir dan petualangan magisnya.",
                "Fantasy", "Inggris",
                "Harry memasuki dunia sihir di Hogwarts dan menemukan rahasia masa lalunya.",
                223, false));

        books.add(new Book(R.drawable.pulang,
                "Pulang", "Tere Liye", "Republika", 2015,
                "9786020822127", "Kisah agen rahasia dan pergulatan batin.",
                "Thriller", "Indonesia",
                "Bujang menjalani hidup sebagai bagian dari organisasi misterius.",
                400, false));

        books.add(new Book(R.drawable.ancika,
                "Ancika: Dia yang Bersamaku 1995", "Pidi Baiq", "Pastel Books", 2021,
                "9786230023674", "Kisah Ancika, setelah Milea.",
                "Romance", "Indonesia",
                "Cinta, kenangan, dan sosok Dilan dari sudut pandang Ancika.",
                325, false));

        books.add(new Book(R.drawable.negeri_5_menara,
                "Negeri 5 Menara", "Ahmad Fuadi", "Gramedia Pustaka Utama", 2009,
                "9789792214459", "Pendidikan, mimpi, dan persahabatan.",
                "Slice of Life", "Indonesia",
                "Alif dan lima sahabatnya bermimpi besar dari pesantren kecil di Jawa.",
                424, false));

        return books;
    }
}