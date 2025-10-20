package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.AkunKeuangan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AkunJpaRepository extends JpaRepository<AkunKeuangan, Integer> {

    // 🔹 Ambil semua akun berdasarkan id pengguna
    List<AkunKeuangan> findByPenggunaIdPengguna(Integer idPengguna);

    // 🔹 Cari akun berdasarkan nama akun dan pengguna
    AkunKeuangan findByNamaAkunAndPenggunaIdPengguna(String namaAkun, Integer idPengguna);

    // 🔹 Cari akun dengan saldo di atas nilai tertentu
    List<AkunKeuangan> findBySaldoGreaterThanEqual(Double saldo);

    // 🔹 Query custom: total saldo milik satu pengguna
    @Query("SELECT SUM(a.saldo) FROM AkunKeuangan a WHERE a.pengguna.idPengguna = :idPengguna")
    Double getTotalSaldoByPengguna(Integer idPengguna);
}
