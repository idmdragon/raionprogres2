package com.android.monagealpha;

public class Users {
    private  String nama;
    private String email;
    private String password;
    private int pemasukan;
    private int pengeluaran;
    private int saldo;
    private int pemasukanSatuan;
    private int pemasukanPengeluaran;
    private int pemasukanSaldo;

    public Users() {
    }

    public Users(String nama, String email, String password, int pemasukan, int pengeluaran, int saldo) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
        this.saldo = saldo;
    }

    public int getPemasukanSatuan() {
        return pemasukanSatuan;
    }

    public void setPemasukanSatuan(int pemasukanSatuan) {
        this.pemasukanSatuan = pemasukanSatuan;
    }

    public int getPemasukanPengeluaran() {
        return pemasukanPengeluaran;
    }

    public void setPemasukanPengeluaran(int pemasukanPengeluaran) {
        this.pemasukanPengeluaran = pemasukanPengeluaran;
    }

    public int getPemasukanSaldo() {
        return pemasukanSaldo;
    }

    public void setPemasukanSaldo(int pemasukanSaldo) {
        this.pemasukanSaldo = pemasukanSaldo;
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

    public int getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(int pemasukan) {
        this.pemasukan = pemasukan;
    }

    public int getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(int pengeluaran) {
        this.pengeluaran = pengeluaran;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
