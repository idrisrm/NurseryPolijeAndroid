package com.example.nurserypolije;

public class ModelTagihan {
    String id_transaksi;
    Integer tanggal_transaksi;
    String alamat_pengiriman;
    String total;

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public Integer getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(Integer tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getAlamat_pengiriman() {
        return alamat_pengiriman;
    }

    public void setAlamat_pengiriman(String alamat_pengiriman) {
        this.alamat_pengiriman = alamat_pengiriman;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


    public ModelTagihan(){
        this.id_transaksi = id_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
        this.alamat_pengiriman = alamat_pengiriman;
        this.total = total;
    }
}
