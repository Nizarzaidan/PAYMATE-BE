package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.Tagihan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository("TagihanJpaRepository")
public interface TagihanJpaRepository extends JpaRepository<Tagihan, Integer> {
    List<Tagihan> findByPenggunaIdPenggunaOrderByIdTagihanAsc(Integer idPengguna);
    List<Tagihan> findByPenggunaIdPenggunaAndStatusOrderByTanggalJatuhTempoAsc(Integer idPengguna, String status);
    List<Tagihan> findByTanggalJatuhTempoAndStatus(LocalDate tanggalJatuhTempo, String status);
    List<Tagihan> findByStatus(String status);
}