package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;

@Entity
@Table(name = "kategori_transaksi")
public class KategoriTransaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategori")
    private Integer idKategori;

    @ManyToOne
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @Column(name = "nama_kategori", nullable = false)
    private String namaKategori;

    @Column(name = "tipe_kategori")
    private String tipeKategori;

    @ManyToOne
    @JoinColumn(name = "id_induk")
    private KategoriTransaksi induk;

    @Column(name = "warna")
    private String warna;

    @Column(name = "ikon")
    private String ikon;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "keterangan")
    private String keterangan;

    public KategoriTransaksi() {
    }

    public KategoriTransaksi(Integer idKategori, Pengguna pengguna, String namaKategori,
                             String tipeKategori, KategoriTransaksi induk, String warna,
                             String ikon, Boolean status, String keterangan) {
        this.idKategori = idKategori;
        this.pengguna = pengguna;
        this.namaKategori = namaKategori;
        this.tipeKategori = tipeKategori;
        this.induk = induk;
        this.warna = warna;
        this.ikon = ikon;
        this.status = status;
        this.keterangan = keterangan;
    }

    // Getters and Setters
    public Integer getIdKategori() { return idKategori; }
    public void setIdKategori(Integer idKategori) { this.idKategori = idKategori; }
    public Pengguna getPengguna() { return pengguna; }
    public void setPengguna(Pengguna pengguna) { this.pengguna = pengguna; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public String getTipeKategori() { return tipeKategori; }
    public void setTipeKategori(String tipeKategori) { this.tipeKategori = tipeKategori; }
    public KategoriTransaksi getInduk() { return induk; }
    public void setInduk(KategoriTransaksi induk) { this.induk = induk; }
    public String getWarna() { return warna; }
    public void setWarna(String warna) { this.warna = warna; }
    public String getIkon() { return ikon; }
    public void setIkon(String ikon) { this.ikon = ikon; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}