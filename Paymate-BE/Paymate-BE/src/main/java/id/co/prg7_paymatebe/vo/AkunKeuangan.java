package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "akun_keuangan")
public class AkunKeuangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_akun")
    private Integer idAkun;

    @ManyToOne
    @JoinColumn(name = "id_pengguna", nullable = false)
    private Pengguna pengguna;

    @Column(name = "nama_akun", nullable = false)
    private String namaAkun;

    @Column(name = "jenis_akun")
    private String jenisAkun;

    @Column(name = "nomor_akun")
    private String nomorAkun;

    @Column(name = "saldo")
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "mata_uang")
    private String mataUang = "IDR";

    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "status")
    private Boolean status = true;

    public AkunKeuangan() {
    }

    public AkunKeuangan(Integer idAkun, Pengguna pengguna, String namaAkun, String jenisAkun,
                        String nomorAkun, BigDecimal saldo, String mataUang, String keterangan,
                        Boolean status) {
        this.idAkun = idAkun;
        this.pengguna = pengguna;
        this.namaAkun = namaAkun;
        this.jenisAkun = jenisAkun;
        this.nomorAkun = nomorAkun;
        this.saldo = saldo;
        this.mataUang = mataUang;
        this.keterangan = keterangan;
        this.status = status;
    }

    // Getters and Setters
    public Integer getIdAkun() { return idAkun; }
    public void setIdAkun(Integer idAkun) { this.idAkun = idAkun; }
    public Pengguna getPengguna() { return pengguna; }
    public void setPengguna(Pengguna pengguna) { this.pengguna = pengguna; }
    public String getNamaAkun() { return namaAkun; }
    public void setNamaAkun(String namaAkun) { this.namaAkun = namaAkun; }
    public String getJenisAkun() { return jenisAkun; }
    public void setJenisAkun(String jenisAkun) { this.jenisAkun = jenisAkun; }
    public String getNomorAkun() { return nomorAkun; }
    public void setNomorAkun(String nomorAkun) { this.nomorAkun = nomorAkun; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public String getMataUang() { return mataUang; }
    public void setMataUang(String mataUang) { this.mataUang = mataUang; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public void setDiperbaruiPada(LocalDateTime now) {
    }
}