package com.example.tp5.Models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {
    private int cover; // Untuk drawable
    private Uri coverUri;      // Untuk gambar dari galeri
    private String judul;
    private String penulis;
    private String penerbit;
    private int tahun;
    private String isbn;
    private String blurb;
    private String genre;
    private String bahasa;
    private String sinopsis;
    private int halaman;
    private boolean isFavorite;

    public Book(Uri coverUri, String judul, String penulis, String penerbit,
                int tahun, String isbn, String blurb, String genre, String bahasa,
                String sinopsis, int halaman, boolean isFavorite) {
        this.coverUri = coverUri;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahun = tahun;
        this.isbn = isbn;
        this.blurb = blurb;
        this.genre = genre;
        this.bahasa = bahasa;
        this.sinopsis = sinopsis;
        this.halaman = halaman;
        this.isFavorite = isFavorite;
    }

    public Book(int cover, String judul, String penulis, String penerbit,
                int tahun, String isbn, String blurb, String genre, String bahasa,
                String sinopsis, int halaman, boolean isFavorite) {
        this.cover = cover;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahun = tahun;
        this.isbn = isbn;
        this.blurb = blurb;
        this.genre = genre;
        this.bahasa = bahasa;
        this.sinopsis = sinopsis;
        this.halaman = halaman;
        this.isFavorite = isFavorite;
    }

    protected Book(Parcel in) {
        cover = in.readInt();
        coverUri = in.readParcelable(Uri.class.getClassLoader());
        judul = in.readString();
        penulis = in.readString();
        penerbit = in.readString();
        tahun = in.readInt();
        isbn = in.readString();
        blurb = in.readString();
        genre = in.readString();
        bahasa = in.readString();
        sinopsis = in.readString();
        halaman = in.readInt();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(cover);
        dest.writeParcelable(coverUri, flags);
        dest.writeString(judul);
        dest.writeString(penulis);
        dest.writeString(penerbit);
        dest.writeInt(tahun);
        dest.writeString(isbn);
        dest.writeString(blurb);
        dest.writeString(genre);
        dest.writeString(bahasa);
        dest.writeString(sinopsis);
        dest.writeInt(halaman);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter dan Setter
    public int getCover() {
        return cover;
    }

    public void setCover(int coverResource) {
        this.cover = coverResource;
    }

    public Uri getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(Uri coverUri) {
        this.coverUri = coverUri;
    }

    public boolean isFromGallery() {
        return coverUri != null;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getHalaman() {
        return halaman;
    }

    public void setHalaman(int halaman) {
        this.halaman = halaman;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}