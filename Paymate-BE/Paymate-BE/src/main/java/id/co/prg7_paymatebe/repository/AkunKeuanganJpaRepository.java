package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.AkunKeuangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("AkunKeuanganJpaRepository")
public interface AkunKeuanganJpaRepository extends JpaRepository<AkunKeuangan, Integer> {
    List<AkunKeuangan> findByPenggunaIdPenggunaOrderByIdAkunAsc(Integer idPengguna);
    List<AkunKeuangan> findByPenggunaIdPenggunaAndStatusTrueOrderByNamaAkunAsc(Integer idPengguna);
    List<AkunKeuangan> findByJenisAkunAndStatusTrue(String jenisAkun);
}