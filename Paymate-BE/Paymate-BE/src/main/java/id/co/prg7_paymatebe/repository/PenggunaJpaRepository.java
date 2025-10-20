package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.Pengguna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PenggunaJpaRepository")
public interface PenggunaJpaRepository extends JpaRepository<Pengguna, Integer> {

    Pengguna getByIdPengguna(Integer idPengguna);

    List<Pengguna> findAllByOrderByIdPenggunaAsc();

    Optional<Pengguna> findByEmail(String email);

    boolean existsByEmail(String email);
}