package com.example.nurserypolije;

public class ModelDikemas {
    String id_transaksi;
    String tanggal_transaksi;
    String alamat_pengiriman;

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getAlamat_pengiriman() {
        return alamat_pengiriman;
    }

    public void setAlamat_pengiriman(String alamat_pengiriman) {
        this.alamat_pengiriman = alamat_pengiriman;
    }

    public ModelDikemas(){
        this.id_transaksi = id_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
        this.alamat_pengiriman = alamat_pengiriman;
    }
}
