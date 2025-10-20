package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "anggaran")
public class Anggaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anggaran")
    private Integer idAnggaran;

    @ManyToOne
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @Column(name = "nama_anggaran", nullable = false)
    private String namaAnggaran;

    @Column(name = "tipe")
    private String tipe;

    @Column(name = "periode_awal")
    private LocalDate periodeAwal;

    @Column(name = "periode_akhir")
    private LocalDate periodeAkhir;

    @Column(name = "nominal_batas", nullable = false)
    private BigDecimal nominalBatas;

    @Column(name = "cakupan")
    private String cakupan = "semua";

    @ManyToOne
    @JoinColumn(name = "id_kategori")
    private KategoriTransaksi kategori;

    @Column(name = "persen_peringatan")
    private Integer persenPeringatan = 80;

    @Column(name = "status")
    private String status = "aktif";

    public Anggaran() {
    }

    public Anggaran(Integer idAnggaran, Pengguna pengguna, String namaAnggaran, String tipe,
                    LocalDate periodeAwal, LocalDate periodeAkhir, BigDecimal nominalBatas,
                    String cakupan, KategoriTransaksi kategori, Integer persenPeringatan,
                    String status) {
        this.idAnggaran = idAnggaran;
        this.pengguna = pengguna;
        this.namaAnggaran = namaAnggaran;
        this.tipe = tipe;
        this.periodeAwal = periodeAwal;
        this.periodeAkhir = periodeAkhir;
        this.nominalBatas = nominalBatas;
        this.cakupan = cakupan;
        this.kategori = kategori;
        this.persenPeringatan = persenPeringatan;
        this.status = status;
    }

    // Getters and Setters
    public Integer getIdAnggaran() { return idAnggaran; }
    public void setIdAnggaran(Integer idAnggaran) { this.idAnggaran = idAnggaran; }
    public Pengguna getPengguna() { return pengguna; }
    public void setPengguna(Pengguna pengguna) { this.pengguna = pengguna; }
    public String getNamaAnggaran() { return namaAnggaran; }
    public void setNamaAnggaran(String namaAnggaran) { this.namaAnggaran = namaAnggaran; }
    public String getTipe() { return tipe; }
    public void setTipe(String tipe) { this.tipe = tipe; }
    public LocalDate getPeriodeAwal() { return periodeAwal; }
    public void setPeriodeAwal(LocalDate periodeAwal) { this.periodeAwal = periodeAwal; }
    public LocalDate getPeriodeAkhir() { return periodeAkhir; }
    public void setPeriodeAkhir(LocalDate periodeAkhir) { this.periodeAkhir = periodeAkhir; }
    public BigDecimal getNominalBatas() { return nominalBatas; }
    public void setNominalBatas(BigDecimal nominalBatas) { this.nominalBatas = nominalBatas; }
    public String getCakupan() { return cakupan; }
    public void setCakupan(String cakupan) { this.cakupan = cakupan; }
    public KategoriTransaksi getKategori() { return kategori; }
    public void setKategori(KategoriTransaksi kategori) { this.kategori = kategori; }
    public Integer getPersenPeringatan() { return persenPeringatan; }
    public void setPersenPeringatan(Integer persenPeringatan) { this.persenPeringatan = persenPeringatan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}