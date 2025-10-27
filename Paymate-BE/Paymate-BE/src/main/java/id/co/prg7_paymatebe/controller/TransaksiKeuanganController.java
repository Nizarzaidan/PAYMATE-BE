package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.TransaksiKeuanganService;
import id.co.prg7_paymatebe.repository.KategoriTransaksiJpaRepository;
import id.co.prg7_paymatebe.vo.TransaksiKeuangan;
import id.co.prg7_paymatebe.vo.KategoriTransaksi;
import id.co.prg7_paymatebe.vo.RekapLaporan;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/transaksi-keuangan")
public class TransaksiKeuanganController {

    @Autowired
    private TransaksiKeuanganService transaksiService;

    @Autowired
    private KategoriTransaksiJpaRepository kategoriTransaksiJpaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransaksiKeuangan(@PathVariable Long id) {
        TransaksiKeuangan transaksi = transaksiService.getTransaksiKeuangan(id);
        return transaksi != null
                ? ResponseEntity.ok(transaksi)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result(404, "Transaksi keuangan tidak ditemukan"));
    }

    @GetMapping("/pengguna/{idPengguna}")
    public ResponseEntity<List<TransaksiKeuangan>> getByPengguna(@PathVariable Integer idPengguna) {
        return ResponseEntity.ok(transaksiService.getTransaksiKeuangansByPengguna(idPengguna));
    }

    @GetMapping("/pengguna/{idPengguna}/tipe/{tipeTransaksi}")
    public ResponseEntity<List<TransaksiKeuangan>> getByTipe(
            @PathVariable Integer idPengguna,
            @PathVariable String tipeTransaksi) {
        return ResponseEntity.ok(transaksiService.getTransaksiKeuangansByTipe(idPengguna, tipeTransaksi));
    }

    @GetMapping("/pengguna/{idPengguna}/periode")
    public ResponseEntity<List<TransaksiKeuangan>> getByPeriode(
            @PathVariable Integer idPengguna,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(transaksiService.getTransaksiKeuangansByPeriode(idPengguna, startDate, endDate));
    }

    @GetMapping("/pengguna/{idPengguna}/bulan-tahun")
    public ResponseEntity<List<TransaksiKeuangan>> getByBulanTahun(
            @PathVariable Integer idPengguna,
            @RequestParam Integer month,
            @RequestParam Integer year) {
        return ResponseEntity.ok(transaksiService.getTransaksiKeuangansByBulanTahun(idPengguna, month, year));
    }

    @GetMapping("/pengguna/{idPengguna}/akun/{idAkun}")
    public ResponseEntity<List<TransaksiKeuangan>> getByAkun(
            @PathVariable Integer idPengguna,
            @PathVariable Integer idAkun) {
        return ResponseEntity.ok(transaksiService.getTransaksiKeuangansByAkun(idPengguna, idAkun));
    }

    @GetMapping("/pengguna/{idPengguna}/kategori/{idKategori}")
    public ResponseEntity<List<TransaksiKeuangan>> getByKategori(
            @PathVariable Integer idPengguna,
            @PathVariable Integer idKategori) {
        return ResponseEntity.ok(transaksiService.getTransaksiKeuangansByKategori(idPengguna, idKategori));
    }

    @PostMapping
    public ResponseEntity<Result> save(@RequestBody TransaksiKeuangan transaksi) {
        try {
            TransaksiKeuangan saved = transaksiService.saveTransaksiKeuangan(transaksi);
            return ResponseEntity.ok(new Result(200, "Transaksi keuangan berhasil disimpan", saved));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Gagal menyimpan transaksi keuangan: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Result> update(@RequestBody TransaksiKeuangan transaksi) {
        boolean updated = transaksiService.updateTransaksiKeuangan(transaksi);
        return updated
                ? ResponseEntity.ok(new Result(200, "Transaksi keuangan berhasil diupdate"))
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result(404, "Transaksi keuangan tidak ditemukan"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> delete(@PathVariable Long id) {
        boolean deleted = transaksiService.deleteTransaksiKeuangan(id);
        return deleted
                ? ResponseEntity.ok(new Result(200, "Transaksi keuangan berhasil dihapus"))
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result(404, "Transaksi keuangan tidak ditemukan"));
    }

    // Endpoint untuk mendapatkan kategori berdasarkan tipe
    @GetMapping("/kategori/tipe/{tipeKategori}")
    public ResponseEntity<List<KategoriTransaksi>> getKategoriByTipe(@PathVariable String tipeKategori) {
        List<KategoriTransaksi> kategori = kategoriTransaksiJpaRepository
                .findByTipeKategoriAndStatusTrueOrderByNamaKategoriAsc(tipeKategori);
        return ResponseEntity.ok(kategori);
    }

    // Rekap laporan
    @GetMapping("/pengguna/{idPengguna}/laporan/rekap")
    public ResponseEntity<List<RekapLaporan>> getRekapLaporan(@PathVariable Integer idPengguna) {
        return ResponseEntity.ok(transaksiService.getRekapLaporanByPengguna(idPengguna));
    }

    @GetMapping("/pengguna/{idPengguna}/laporan/rekap/{periode}")
    public ResponseEntity<?> getRekapLaporanByPeriode(
            @PathVariable Integer idPengguna,
            @PathVariable String periode) {
        RekapLaporan rekap = transaksiService.getRekapLaporanByPenggunaAndPeriode(idPengguna, periode);
        return rekap != null
                ? ResponseEntity.ok(rekap)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Result(404, "Data laporan tidak ditemukan untuk periode " + periode));
    }
}