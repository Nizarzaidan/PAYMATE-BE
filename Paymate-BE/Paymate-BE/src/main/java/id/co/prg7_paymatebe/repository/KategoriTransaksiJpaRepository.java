package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.KategoriTransaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("KategoriTransaksiJpaRepository")
public interface KategoriTransaksiJpaRepository extends JpaRepository<KategoriTransaksi, Integer> {
    List<KategoriTransaksi> findByPenggunaIdPenggunaOrderByIdKategoriAsc(Integer idPengguna);
    List<KategoriTransaksi> findByPenggunaIdPenggunaAndTipeKategoriAndStatusTrueOrderByNamaKategoriAsc(Integer idPengguna, String tipeKategori);
    List<KategoriTransaksi> findByIndukIsNullAndStatusTrue();
    List<KategoriTransaksi> findByIndukIdKategoriAndStatusTrue(Integer idInduk);
}