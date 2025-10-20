package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.Anggaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("AnggaranJpaRepository")
public interface AnggaranJpaRepository extends JpaRepository<Anggaran, Integer> {
    List<Anggaran> findByPenggunaIdPenggunaOrderByIdAnggaranAsc(Integer idPengguna);
    List<Anggaran> findByPenggunaIdPenggunaAndStatusOrderByPeriodeAwalAsc(Integer idPengguna, String status);
    List<Anggaran> findByStatus(String status);
}