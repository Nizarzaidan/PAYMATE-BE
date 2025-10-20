package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "target_tabungan")
public class TargetTabungan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_target")
    private Integer idTarget;

    @ManyToOne
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @Column(name = "nama_target", nullable = false)
    private String namaTarget;

    @Column(name = "target_nominal", nullable = false)
    private BigDecimal targetNominal;

    @Column(name = "tanggal_mulai", nullable = false)
    private LocalDate tanggalMulai;

    @Column(name = "tanggal_selesai")
    private LocalDate tanggalSelesai;

    @Column(name = "status")
    private String status = "berjalan";

    @Column(name = "catatan")
    private String catatan;

    public TargetTabungan() {
    }

    public TargetTabungan(Integer idTarget, Pengguna pengguna, String namaTarget,
                          BigDecimal targetNominal, LocalDate tanggalMulai,
                          LocalDate tanggalSelesai, String status, String catatan) {
        this.idTarget = idTarget;
        this.pengguna = pengguna;
        this.namaTarget = namaTarget;
        this.targetNominal = targetNominal;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.status = status;
        this.catatan = catatan;
    }

    // Getters and Setters
    public Integer getIdTarget() { return idTarget; }
    public void setIdTarget(Integer idTarget) { this.idTarget = idTarget; }
    public Pengguna getPengguna() { return pengguna; }
    public void setPengguna(Pengguna pengguna) { this.pengguna = pengguna; }
    public String getNamaTarget() { return namaTarget; }
    public void setNamaTarget(String namaTarget) { this.namaTarget = namaTarget; }
    public BigDecimal getTargetNominal() { return targetNominal; }
    public void setTargetNominal(BigDecimal targetNominal) { this.targetNominal = targetNominal; }
    public LocalDate getTanggalMulai() { return tanggalMulai; }
    public void setTanggalMulai(LocalDate tanggalMulai) { this.tanggalMulai = tanggalMulai; }
    public LocalDate getTanggalSelesai() { return tanggalSelesai; }
    public void setTanggalSelesai(LocalDate tanggalSelesai) { this.tanggalSelesai = tanggalSelesai; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}