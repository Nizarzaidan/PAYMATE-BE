package id.co.prg7_paymatebe.repository;

import id.co.prg7_paymatebe.vo.TransaksiKeuangan;
import id.co.prg7_paymatebe.vo.RekapLaporan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository("TransaksiKeuanganJpaRepository")
public interface TransaksiKeuanganJpaRepository extends JpaRepository<TransaksiKeuangan, Long> {

    List<TransaksiKeuangan> findByPenggunaIdPenggunaOrderByTanggalTransaksiDesc(Integer idPengguna);

    List<TransaksiKeuangan> findByPenggunaIdPenggunaAndTipeTransaksiOrderByTanggalTransaksiDesc(
            Integer idPengguna, String tipeTransaksi);

    List<TransaksiKeuangan> findByPenggunaIdPenggunaAndTanggalTransaksiBetweenOrderByTanggalTransaksiDesc(
            Integer idPengguna, LocalDateTime startDate, LocalDateTime endDate);

    List<TransaksiKeuangan> findByPenggunaIdPenggunaAndAkunIdAkunOrderByTanggalTransaksiDesc(
            Integer idPengguna, Integer idAkun);

    List<TransaksiKeuangan> findByPenggunaIdPenggunaAndKategoriIdKategoriOrderByTanggalTransaksiDesc(
            Integer idPengguna, Integer idKategori);

    @Query("SELECT t FROM TransaksiKeuangan t WHERE t.pengguna.idPengguna = :idPengguna " +
            "AND MONTH(t.tanggalTransaksi) = :month AND YEAR(t.tanggalTransaksi) = :year " +
            "ORDER BY t.tanggalTransaksi DESC")
    List<TransaksiKeuangan> findByPenggunaAndMonthYear(
            @Param("idPengguna") Integer idPengguna,
            @Param("month") Integer month,
            @Param("year") Integer year);

    @Query(value =
            "SELECT id_pengguna as idPengguna, " +
                    "FORMAT(tanggal_transaksi, 'yyyy-MM') as periodeBulan, " +
                    "SUM(CASE WHEN tipe_transaksi = 'pemasukan' THEN nominal ELSE 0 END) as totalPemasukan, " +
                    "SUM(CASE WHEN tipe_transaksi = 'pengeluaran' THEN nominal ELSE 0 END) as totalPengeluaran " +
                    "FROM transaksi_keuangan " +
                    "WHERE id_pengguna = :idPengguna " +
                    "GROUP BY id_pengguna, FORMAT(tanggal_transaksi, 'yyyy-MM') " +
                    "ORDER BY periodeBulan DESC",
            nativeQuery = true)
    List<RekapLaporan> findRekapLaporanByPengguna(@Param("idPengguna") Integer idPengguna);

    @Query(value =
            "SELECT id_pengguna as idPengguna, " +
                    "FORMAT(tanggal_transaksi, 'yyyy-MM') as periodeBulan, " +
                    "SUM(CASE WHEN tipe_transaksi = 'pemasukan' THEN nominal ELSE 0 END) as totalPemasukan, " +
                    "SUM(CASE WHEN tipe_transaksi = 'pengeluaran' THEN nominal ELSE 0 END) as totalPengeluaran " +
                    "FROM transaksi_keuangan " +
                    "WHERE id_pengguna = :idPengguna AND FORMAT(tanggal_transaksi, 'yyyy-MM') = :periode " +
                    "GROUP BY id_pengguna, FORMAT(tanggal_transaksi, 'yyyy-MM')",
            nativeQuery = true)
    RekapLaporan findRekapLaporanByPenggunaAndPeriode(
            @Param("idPengguna") Integer idPengguna,
            @Param("periode") String periode);

    // ✅ Tambahan: total nominal berdasarkan tipe transaksi (pemasukan/pengeluaran)
    @Query("SELECT COALESCE(SUM(t.nominal), 0) FROM TransaksiKeuangan t WHERE LOWER(t.tipeTransaksi) = LOWER(:tipe)")
    BigDecimal sumByTipe(@Param("tipe") String tipe);

    // ✅ Versi tambahan (optional): total berdasarkan tipe + pengguna
    @Query("SELECT COALESCE(SUM(t.nominal), 0) FROM TransaksiKeuangan t WHERE LOWER(t.tipeTransaksi) = LOWER(:tipe) AND t.pengguna.idPengguna = :idPengguna")
    BigDecimal sumByTipeAndPengguna(@Param("tipe") String tipe, @Param("idPengguna") Integer idPengguna);
}
