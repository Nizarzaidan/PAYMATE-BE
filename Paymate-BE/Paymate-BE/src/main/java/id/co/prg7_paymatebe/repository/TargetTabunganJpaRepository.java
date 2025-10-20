package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.TargetTabungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("TargetTabunganJpaRepository")
public interface TargetTabunganJpaRepository extends JpaRepository<TargetTabungan, Integer> {
    List<TargetTabungan> findByPenggunaIdPenggunaOrderByIdTargetAsc(Integer idPengguna);
    List<TargetTabungan> findByPenggunaIdPenggunaAndStatusOrderByTanggalMulaiAsc(Integer idPengguna, String status);
    List<TargetTabungan> findByStatus(String status);
}