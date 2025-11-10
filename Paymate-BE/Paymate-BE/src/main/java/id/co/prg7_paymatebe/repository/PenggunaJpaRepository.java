package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.Pengguna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PenggunaJpaRepository")
public interface PenggunaJpaRepository extends JpaRepository<Pengguna, Integer> {

    // Method untuk get pengguna by ID (custom)
    Pengguna getByIdPengguna(Integer idPengguna);

    // Method untuk get all pengguna sorted by ID
    List<Pengguna> findAllByOrderByIdPenggunaAsc();

    // Method untuk find by email
    Optional<Pengguna> findByEmail(String email);

    // Method untuk cek email exists
    boolean existsByEmail(String email);

    // Method untuk leaderboard (optional - untuk fitur lanjutan)
    List<Pengguna> findAllByOrderByPoinDesc();
}