package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaksi_keuangann")
public class TransaksiKeuangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaksi")
    private Long idTransaksi;

    @ManyToOne
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @ManyToOne
    @JoinColumn(name = "id_akun", nullable = false)
    private AkunKeuangan akun;

    @Column(name = "tipe_transaksi")
    private String tipeTransaksi;

    @ManyToOne
    @JoinColumn(name = "id_kategori")
    private KategoriTransaksi kategori;

    @Column(name = "nominal", nullable = false)
    private BigDecimal nominal;

    @Column(name = "tanggal_transaksi")
    private LocalDateTime tanggalTransaksi;

    @Column(name = "catatan")
    private String catatan;

    @Column(name = "dibuat_pada")
    private LocalDateTime dibuatPada;

    @Column(name = "diperbarui_pada")
    private LocalDateTime diperbaruiPada;

    public TransaksiKeuangan() {
    }

    public TransaksiKeuangan(Long idTransaksi, Pengguna pengguna, AkunKeuangan akun, String tipeTransaksi,
                             KategoriTransaksi kategori, BigDecimal nominal, LocalDateTime tanggalTransaksi,
                             String catatan,
                             LocalDateTime dibuatPada, LocalDateTime diperbaruiPada) {
        this.idTransaksi = idTransaksi;
        this.pengguna = pengguna;
        this.akun = akun;
        this.tipeTransaksi = tipeTransaksi;
        this.kategori = kategori;
        this.nominal = nominal;
        this.tanggalTransaksi = tanggalTransaksi;
        this.catatan = catatan;
        this.dibuatPada = dibuatPada;
        this.diperbaruiPada = diperbaruiPada;
    }

    // Getters and Setters
    public Long getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(Long idTransaksi) { this.idTransaksi = idTransaksi; }
    public Pengguna getPengguna() { return pengguna; }
    public void setPengguna(Pengguna pengguna) { this.pengguna = pengguna; }
    public AkunKeuangan getAkun() { return akun; }
    public void setAkun(AkunKeuangan akun) { this.akun = akun; }
    public String getTipeTransaksi() { return tipeTransaksi; }
    public void setTipeTransaksi(String tipeTransaksi) { this.tipeTransaksi = tipeTransaksi; }
    public KategoriTransaksi getKategori() { return kategori; }
    public void setKategori(KategoriTransaksi kategori) { this.kategori = kategori; }
    public BigDecimal getNominal() { return nominal; }
    public void setNominal(BigDecimal nominal) { this.nominal = nominal; }
    public LocalDateTime getTanggalTransaksi() { return tanggalTransaksi; }
    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) { this.tanggalTransaksi = tanggalTransaksi; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
    public LocalDateTime getDibuatPada() { return dibuatPada; }
    public void setDibuatPada(LocalDateTime dibuatPada) { this.dibuatPada = dibuatPada; }
    public LocalDateTime getDiperbaruiPada() { return diperbaruiPada; }
    public void setDiperbaruiPada(LocalDateTime diperbaruiPada) { this.diperbaruiPada = diperbaruiPada; }
}