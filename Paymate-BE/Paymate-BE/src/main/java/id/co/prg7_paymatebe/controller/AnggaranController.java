package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.AnggaranService;
import id.co.prg7_paymatebe.vo.Anggaran;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/anggaran")
public class AnggaranController {

    @Autowired
    private AnggaranService mAnggaranService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnggaran(@PathVariable Integer id) {
        Anggaran anggaran = mAnggaranService.getAnggaran(id);
        if (anggaran != null) {
            return ResponseEntity.ok(anggaran);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Anggaran tidak ditemukan"));
        }
    }

    @GetMapping("/pengguna/{idPengguna}")
    public List<Anggaran> getAnggaransByPengguna(@PathVariable Integer idPengguna) {
        return mAnggaranService.getAnggaransByPengguna(idPengguna);
    }

    @GetMapping("/pengguna/{idPengguna}/aktif")
    public List<Anggaran> getAnggaransAktifByPengguna(@PathVariable Integer idPengguna) {
        return mAnggaranService.getAnggaransAktifByPengguna(idPengguna);
    }

    @PostMapping
    public ResponseEntity<Result> saveAnggaran(@RequestBody Anggaran anggaranParam) {
        try {
            Anggaran result = mAnggaranService.saveAnggaran(anggaranParam);
            return ResponseEntity.ok(new Result(200, "Anggaran berhasil disimpan", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Gagal menyimpan anggaran: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Result> updateAnggaran(@RequestBody Anggaran anggaranParam) {
        boolean isSuccess = mAnggaranService.updateAnggaran(anggaranParam);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Anggaran berhasil diupdate"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Anggaran tidak ditemukan"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteAnggaran(@PathVariable Integer id) {
        boolean isSuccess = mAnggaranService.deleteAnggaran(id);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Anggaran berhasil dinonaktifkan"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Anggaran tidak ditemukan"));
        }
    }

    @PutMapping("/{id}/aktifkan")
    public ResponseEntity<Result> aktifkanAnggaran(@PathVariable Integer id) {
        Anggaran anggaran = mAnggaranService.getAnggaran(id);
        if (anggaran != null) {
            anggaran.setStatus("aktif");
            mAnggaranService.updateAnggaran(anggaran);
            return ResponseEntity.ok(new Result(200, "Anggaran berhasil diaktifkan"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Anggaran tidak ditemukan"));
        }
    }
}