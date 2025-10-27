package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.TargetTabungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository("TargetTabunganJpaRepository")
public interface TargetTabunganJpaRepository extends JpaRepository<TargetTabungan, Integer> {
    List<TargetTabungan> findByPenggunaIdPenggunaOrderByIdTargetAsc(Integer idPengguna);
    List<TargetTabungan> findByPenggunaIdPenggunaAndStatusOrderByTanggalMulaiAsc(Integer idPengguna, String status);
    List<TargetTabungan> findByStatus(String status);

    // Query untuk update nominal sekarang
    @Modifying
    @Query("UPDATE TargetTabungan t SET t.nominalSekarang = :nominalSekarang WHERE t.idTarget = :idTarget")
    void updateNominalSekarang(@Param("idTarget") Integer idTarget, @Param("nominalSekarang") BigDecimal nominalSekarang);

    // Query untuk mencari target yang masih aktif
    @Query("SELECT t FROM TargetTabungan t WHERE t.pengguna.idPengguna = :idPengguna AND t.status = 'berjalan' ORDER BY t.tanggalSelesai ASC")
    List<TargetTabungan> findTargetAktifByPengguna(@Param("idPengguna") Integer idPengguna);
}