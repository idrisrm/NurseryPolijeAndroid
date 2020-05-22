package com.example.nurserypolije;

public class ModelKeranjang {
    String id_bunga;
    String nama_bunga;
    String id_kategori;
    String totalHarga;
    String foto_bunga;
    Integer jumlah;

    public String getId_bunga() {
        return id_bunga;
    }

    public void setId_bunga(String id_bunga) {
        this.id_bunga = id_bunga;
    }

    public String getNama_bunga() {
        return nama_bunga;
    }

    public void setNama_bunga(String nama_bunga) {
        this.nama_bunga = nama_bunga;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String gettotalHarga() {
        return totalHarga;
    }

    public void settotalHarga(String harga) {
        this.totalHarga = harga;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getFoto_bunga() {
        return foto_bunga;
    }

    public void setFoto_bunga(String foto_bunga) {
        this.foto_bunga = foto_bunga;
    }

    public ModelKeranjang(){
        this.id_bunga = id_bunga;
        this.nama_bunga = nama_bunga;
        this.id_kategori = id_kategori;
        this.totalHarga = totalHarga;
        this.jumlah = jumlah;
        this.foto_bunga = foto_bunga;
    }
}
