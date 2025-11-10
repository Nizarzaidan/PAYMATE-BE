package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tagihan")
public class Tagihan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tagihan")
    private Integer idTagihan;

    @ManyToOne
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @Column(name = "nama_tagihan", nullable = false)
    private String namaTagihan;

    @Column(name = "nominal", nullable = false)
    private BigDecimal nominal;
    @Column(name = "catatan", nullable = false)
    private String catatan;

    @Column(name = "tanggal_jatuh_tempo", nullable = false)
    private LocalDate tanggalJatuhTempo;

    @Column(name = "tipe_perulangan")
    private String tipePerulangan;

    @Column(name = "terakhir_dikirim")
    private LocalDateTime terakhirDikirim;

    @Column(name = "status")
    private String status = "Belum Lunas"; // Default status

    public Tagihan() {
    }

    public Tagihan(Integer idTagihan, Pengguna pengguna, String namaTagihan, BigDecimal nominal,String catatan,
                   LocalDate tanggalJatuhTempo, String tipePerulangan, LocalDateTime terakhirDikirim,
                   String status) {
        this.idTagihan = idTagihan;
        this.pengguna = pengguna;
        this.namaTagihan = namaTagihan;
        this.nominal = nominal;
        this.catatan = catatan;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tipePerulangan = tipePerulangan;
        this.terakhirDikirim = terakhirDikirim;
        this.status = status;
    }

    // Getters and Setters
    public Integer getIdTagihan() { return idTagihan; }
    public void setIdTagihan(Integer idTagihan) { this.idTagihan = idTagihan; }
    public Pengguna getPengguna() { return pengguna; }
    public void setPengguna(Pengguna pengguna) { this.pengguna = pengguna; }
    public String getNamaTagihan() { return namaTagihan; }
    public void setNamaTagihan(String namaTagihan) { this.namaTagihan = namaTagihan; }
    public BigDecimal getNominal() { return nominal; }
    public void setNominal(BigDecimal nominal) { this.nominal = nominal; }
    public LocalDate getTanggalJatuhTempo() { return tanggalJatuhTempo; }
    public void setTanggalJatuhTempo(LocalDate tanggalJatuhTempo) { this.tanggalJatuhTempo = tanggalJatuhTempo; }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getTipePerulangan() { return tipePerulangan; }
    public void setTipePerulangan(String tipePerulangan) { this.tipePerulangan = tipePerulangan; }
    public LocalDateTime getTerakhirDikirim() { return terakhirDikirim; }
    public void setTerakhirDikirim(LocalDateTime terakhirDikirim) { this.terakhirDikirim = terakhirDikirim; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}