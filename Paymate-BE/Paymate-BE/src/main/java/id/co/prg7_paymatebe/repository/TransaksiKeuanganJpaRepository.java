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

    // ✅ Query untuk rekap laporan berdasarkan ID pengguna
    @Query(value =
            "SELECT t.id_pengguna AS idPengguna, " +
                    "CONVERT(char(7), t.tanggal_transaksi, 120) AS periodeBulan, " + // ganti FORMAT() → CONVERT() agar aman di semua versi SQL Server
                    "SUM(CASE WHEN t.tipe_transaksi = 'pemasukan' THEN t.nominal ELSE 0 END) AS totalPemasukan, " +
                    "SUM(CASE WHEN t.tipe_transaksi = 'pengeluaran' THEN t.nominal ELSE 0 END) AS totalPengeluaran " +
                    "FROM transaksi_keuangann t " + // ✅ pakai nama tabel aslinya
                    "WHERE t.id_pengguna = :idPengguna " +
                    "GROUP BY t.id_pengguna, CONVERT(char(7), t.tanggal_transaksi, 120) " +
                    "ORDER BY periodeBulan DESC",
            nativeQuery = true)
    List<RekapLaporan> findRekapLaporanByPengguna(@Param("idPengguna") Integer idPengguna);

    // ✅ Query untuk rekap laporan berdasarkan periode tertentu
    @Query(value =
            "SELECT t.id_pengguna AS idPengguna, " +
                    "CONVERT(char(7), t.tanggal_transaksi, 120) AS periodeBulan, " +
                    "SUM(CASE WHEN t.tipe_transaksi = 'pemasukan' THEN t.nominal ELSE 0 END) AS totalPemasukan, " +
                    "SUM(CASE WHEN t.tipe_transaksi = 'pengeluaran' THEN t.nominal ELSE 0 END) AS totalPengeluaran " +
                    "FROM transaksi_keuangann t " +
                    "WHERE t.id_pengguna = :idPengguna AND CONVERT(char(7), t.tanggal_transaksi, 120) = :periode " +
                    "GROUP BY t.id_pengguna, CONVERT(char(7), t.tanggal_transaksi, 120)",
            nativeQuery = true)
    RekapLaporan findRekapLaporanByPenggunaAndPeriode(
            @Param("idPengguna") Integer idPengguna,
            @Param("periode") String periode);

    // ✅ Tambahan: total nominal berdasarkan tipe transaksi
    @Query("SELECT COALESCE(SUM(t.nominal), 0) FROM TransaksiKeuangan t WHERE LOWER(t.tipeTransaksi) = LOWER(:tipe)")
    BigDecimal sumByTipe(@Param("tipe") String tipe);

    // ✅ Tambahan: total berdasarkan tipe + pengguna
    @Query("SELECT COALESCE(SUM(t.nominal), 0) FROM TransaksiKeuangan t WHERE LOWER(t.tipeTransaksi) = LOWER(:tipe) AND t.pengguna.idPengguna = :idPengguna")
    BigDecimal sumByTipeAndPengguna(@Param("tipe") String tipe, @Param("idPengguna") Integer idPengguna);
}
