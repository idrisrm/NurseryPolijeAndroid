package com.example.nurserypolije;

public class ModelHome {
    String id_bunga;
    String nama_bunga;
    String id_kategori;
    String harga;
    Integer stok;
    String foto_bunga;
    String video_bunga;
    String cara_perawatan;
    String deskripsi;

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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getFoto_bunga() {
        return foto_bunga;
    }

    public void setFoto_bunga(String foto_bunga) {
        this.foto_bunga = foto_bunga;
    }

    public String getVideo_bunga() {
        return video_bunga;
    }

    public void setVideo_bunga(String video_bunga) {
        this.video_bunga = video_bunga;
    }

    public String getCara_perawatan() {
        return cara_perawatan;
    }

    public void setCara_perawatan(String cara_perawatan) {
        this.cara_perawatan = cara_perawatan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public ModelHome() {
        this.id_bunga = id_bunga;
        this.nama_bunga = nama_bunga;
        this.id_kategori = id_kategori;
        this.harga = harga;
        this.stok = stok;
        this.foto_bunga = foto_bunga;
        this.video_bunga = video_bunga;
        this.cara_perawatan = cara_perawatan;
        this.deskripsi = deskripsi;
    }
}
