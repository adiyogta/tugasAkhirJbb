package com.example.tugasakhirjbb;

public class UserData {

    String nama, email, password, notelp;

    public UserData() {

    }

    public UserData(String nama, String email, String password, String notelp) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.notelp = notelp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }
}
