package com.example.tugasakhirjbb;

import android.net.Uri;

class barangdata {

    public String namaB;
    public String ukuranB;
    public String kekuranganB;
    public String lokasiB;
    public String hargaB;
    public String keteranganB;
    public String imageURL;

    public barangdata(String namaBr, String ukuranBr, String kekuranganBr, String lokasiBr, String hargaBr, String keteranganBBr, String url) {

        this.namaB = namaBr;
        this.ukuranB = ukuranBr;
        this.kekuranganB = kekuranganBr;
        this.lokasiB = lokasiBr;
        this.hargaB = hargaBr;
        this.keteranganB = keteranganBBr;
        this.imageURL = url;

    }

    public String getNamaB() {
        return namaB;
    }

    public void setNamaB(String namaB) {
        this.namaB = namaB;
    }

    public String getUkuranB() {
        return ukuranB;
    }

    public void setUkuranB(String ukuranB) {
        this.ukuranB = ukuranB;
    }

    public String getKekuranganB() {
        return kekuranganB;
    }

    public void setKekuranganB(String kekuranganB) {
        this.kekuranganB = kekuranganB;
    }

    public String getLokasiB() {
        return lokasiB;
    }

    public void setLokasiB(String lokasiB) {
        this.lokasiB = lokasiB;
    }

    public String getHargaB() {
        return hargaB;
    }

    public void setHargaB(String hargaB) {
        this.hargaB = hargaB;
    }

    public String getKeteranganB() {
        return keteranganB;
    }

    public void setKeteranganB(String keteranganB) {
        this.keteranganB = keteranganB;
    }

    public Uri getImageURL() {
        return Uri.parse(imageURL);
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
