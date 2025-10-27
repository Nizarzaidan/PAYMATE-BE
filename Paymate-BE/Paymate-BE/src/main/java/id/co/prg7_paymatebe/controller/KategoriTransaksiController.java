package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.KategoriTransaksiService;
import id.co.prg7_paymatebe.vo.KategoriTransaksi;
import id.co.prg7_paymatebe.vo.Pengguna;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/kategori-transaksi")
public class KategoriTransaksiController {

    @Autowired
    private KategoriTransaksiService mKategoriTransaksiService;

    // Endpoint yang sudah ada...
    @GetMapping("/{id}")
    public ResponseEntity<?> getKategoriTransaksi(@PathVariable Integer id) {
        KategoriTransaksi kategori = mKategoriTransaksiService.getKategoriTransaksi(id);
        if (kategori != null) {
            return ResponseEntity.ok(kategori);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Kategori Transaksi tidak ditemukan"));
        }
    }

    @GetMapping("/pengguna/{idPengguna}")
    public List<KategoriTransaksi> getKategoriTransaksisByPengguna(@PathVariable Integer idPengguna) {
        return mKategoriTransaksiService.getKategoriTransaksisByPengguna(idPengguna);
    }

    @GetMapping("/pengguna/{idPengguna}/tipe/{tipeKategori}")
    public List<KategoriTransaksi> getKategoriTransaksisByTipe(
            @PathVariable Integer idPengguna,
            @PathVariable String tipeKategori) {
        return mKategoriTransaksiService.getKategoriTransaksisByTipe(idPengguna, tipeKategori);
    }

    @PostMapping
    public ResponseEntity<Result> saveKategoriTransaksi(@RequestBody KategoriTransaksi kategoriTransaksiParam) {
        try {
            KategoriTransaksi result = mKategoriTransaksiService.saveKategoriTransaksi(kategoriTransaksiParam);
            return ResponseEntity.ok(new Result(200, "Kategori Transaksi berhasil disimpan", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Gagal menyimpan kategori transaksi: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Result> updateKategoriTransaksi(@RequestBody KategoriTransaksi kategoriTransaksiParam) {
        boolean isSuccess = mKategoriTransaksiService.updateKategoriTransaksi(kategoriTransaksiParam);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Kategori Transaksi berhasil diupdate"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Kategori Transaksi tidak ditemukan"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteKategoriTransaksi(@PathVariable Integer id) {
        boolean isSuccess = mKategoriTransaksiService.deleteKategoriTransaksi(id);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Kategori Transaksi berhasil dihapus"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Kategori Transaksi tidak ditemukan"));
        }
    }

    // Endpoint baru untuk initialize default categories
    @PostMapping("/initialize-default/{idPengguna}")
    public ResponseEntity<Result> initializeDefaultCategories(@PathVariable Integer idPengguna) {
        try {
            // Buat object pengguna dummy untuk inisialisasi
            Pengguna pengguna = new Pengguna();
            pengguna.setIdPengguna(idPengguna);

            mKategoriTransaksiService.createDefaultCategories(pengguna);
            return ResponseEntity.ok(new Result(200, "Kategori default berhasil diinisialisasi"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Gagal menginisialisasi kategori default: " + e.getMessage()));
        }
    }
}