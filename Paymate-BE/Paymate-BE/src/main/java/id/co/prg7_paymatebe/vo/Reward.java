package id.co.prg7_paymatebe.vo;

import jakarta.persistence.*;

@Entity
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reward")
    private Integer idReward;

    @Column(name = "nama_reward", nullable = false)
    private String namaReward;

    @Column(name = "kriteria")
    private String kriteria;

    @Column(name = "poin")
    private Integer poin = 0;

    @Column(name = "deskripsi")
    private String deskripsi;

    public Reward() {
    }

    public Reward(Integer idReward, String namaReward, String kriteria, Integer poin, String deskripsi) {
        this.idReward = idReward;
        this.namaReward = namaReward;
        this.kriteria = kriteria;
        this.poin = poin;
        this.deskripsi = deskripsi;
    }

    // Getters and Setters
    public Integer getIdReward() { return idReward; }
    public void setIdReward(Integer idReward) { this.idReward = idReward; }
    public String getNamaReward() { return namaReward; }
    public void setNamaReward(String namaReward) { this.namaReward = namaReward; }
    public String getKriteria() { return kriteria; }
    public void setKriteria(String kriteria) { this.kriteria = kriteria; }
    public Integer getPoin() { return poin; }
    public void setPoin(Integer poin) { this.poin = poin; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
}