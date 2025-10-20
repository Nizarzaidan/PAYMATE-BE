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

    @Column(name = "zona_waktu")
    private String zonaWaktu;

    @Column(name = "peran")
    private String peran = "user";

    @Column(name = "status_aktif")
    private Boolean statusAktif = true;

    @Column(name = "mata_uang")
    private String mataUang = "IDR";

    @Column(name = "bahasa")
    private String bahasa = "id";

    @Column(name = "foto_profil")
    private String fotoProfil;

    @Column(name = "preferensi_notifikasi")
    private String preferensiNotifikasi;

    public Pengguna() {
    }

    public Pengguna(Integer idPengguna, String email, String kataSandiHash, String namaLengkap,
                    String telepon, LocalDateTime tanggalDaftar, String zonaWaktu, String peran,
                    Boolean statusAktif, String mataUang, String bahasa, String fotoProfil,
                    String preferensiNotifikasi) {
        this.idPengguna = idPengguna;
        this.email = email;
        this.kataSandiHash = kataSandiHash;
        this.namaLengkap = namaLengkap;
        this.telepon = telepon;
        this.tanggalDaftar = tanggalDaftar;
        this.zonaWaktu = zonaWaktu;
        this.peran = peran;
        this.statusAktif = statusAktif;
        this.mataUang = mataUang;
        this.bahasa = bahasa;
        this.fotoProfil = fotoProfil;
        this.preferensiNotifikasi = preferensiNotifikasi;
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

    public String getZonaWaktu() {
        return zonaWaktu;
    }

    public void setZonaWaktu(String zonaWaktu) {
        this.zonaWaktu = zonaWaktu;
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

    public String getMataUang() {
        return mataUang;
    }

    public void setMataUang(String mataUang) {
        this.mataUang = mataUang;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getFotoProfil() {
        return fotoProfil;
    }

    public void setFotoProfil(String fotoProfil) {
        this.fotoProfil = fotoProfil;
    }

    public String getPreferensiNotifikasi() {
        return preferensiNotifikasi;
    }

    public void setPreferensiNotifikasi(String preferensiNotifikasi) {
        this.preferensiNotifikasi = preferensiNotifikasi;
    }
}