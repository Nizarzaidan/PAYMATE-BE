package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pengguna")
public class Pengguna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pengguna")
    private Integer idPengguna;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "kata_sandi_hash", nullable = false)
    private String kataSandiHash;

    @Column(name = "nama_lengkap", nullable = false)
    private String namaLengkap;

    @Column(name = "telepon")
    private String telepon;

    @Column(name = "tanggal_daftar")
    private LocalDateTime tanggalDaftar;

    @Column(name = "peran")
    private String peran = "user";

    @Column(name = "status_aktif")
    private Boolean statusAktif = true;

    @Column(name = "foto_profil")
    private String fotoProfil;

    @Column(name = "nama_panggilan")
    private String namaPanggilan;

    @Column(name = "jenis_kelamin")
    private String jenisKelamin;

    @Column(name = "tanggal_lahir")
    private LocalDateTime tanggalLahir;

    // ===== REWARD FIELDS - BARU =====
    @Column(name = "poin")
    private Integer poin = 0;

    @Column(name = "medali")
    private Integer medali = 0;
    // ================================

    public Pengguna() {
    }

    public Pengguna(Integer idPengguna, String email, String kataSandiHash, String namaLengkap,
                    String telepon, LocalDateTime tanggalDaftar, String peran,
                    Boolean statusAktif, String fotoProfil, String namaPanggilan,
                    String jenisKelamin, LocalDateTime tanggalLahir, Integer poin, Integer medali) {
        this.idPengguna = idPengguna;
        this.email = email;
        this.kataSandiHash = kataSandiHash;
        this.namaLengkap = namaLengkap;
        this.telepon = telepon;
        this.tanggalDaftar = tanggalDaftar;
        this.peran = peran;
        this.statusAktif = statusAktif;
        this.fotoProfil = fotoProfil;
        this.namaPanggilan = namaPanggilan;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.poin = poin;
        this.medali = medali;
    }

    // Getters and Setters
    public Integer getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(Integer idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKataSandiHash() {
        return kataSandiHash;
    }

    public void setKataSandiHash(String kataSandiHash) {
        this.kataSandiHash = kataSandiHash;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public LocalDateTime getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDateTime tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getPeran() {
        return peran;
    }

    public void setPeran(String peran) {
        this.peran = peran;
    }

    public Boolean getStatusAktif() {
        return statusAktif;
    }

    public void setStatusAktif(Boolean statusAktif) {
        this.statusAktif = statusAktif;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public String getNamaPanggilan() {
        return namaPanggilan;
    }

    public void setNamaPanggilan(String namaPanggilan) {
        this.namaPanggilan = namaPanggilan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public LocalDateTime getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDateTime tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    // ===== REWARD GETTERS & SETTERS - BARU =====
    public Integer getPoin() {
        return poin != null ? poin : 0;
    }

    public void setPoin(Integer poin) {
        this.poin = poin;
    }

    public Integer getMedali() {
        return medali != null ? medali : 0;
    }

    public void setMedali(Integer medali) {
        this.medali = medali;
    }
    // ============================================

    @Override
    public String toString() {
        return "Pengguna{" +
                "idPengguna=" + idPengguna +
                ", email='" + email + '\'' +
                ", namaLengkap='" + namaLengkap + '\'' +
                ", poin=" + poin +
                ", medali=" + medali +
                '}';
    }
}