package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.TargetTabunganService;
import id.co.prg7_paymatebe.vo.TargetTabungan;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/target-tabungan")
public class TargetTabunganController {

    @Autowired
    private TargetTabunganService mTargetTabunganService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTargetTabungan(@PathVariable Integer id) {
        TargetTabungan target = mTargetTabunganService.getTargetTabungan(id);
        if (target != null) {
            return ResponseEntity.ok(target);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Target Tabungan tidak ditemukan"));
        }
    }

    @GetMapping("/pengguna/{idPengguna}")
    public List<TargetTabungan> getTargetTabungansByPengguna(@PathVariable Integer idPengguna) {
        return mTargetTabunganService.getTargetTabungansByPengguna(idPengguna);
    }

    @GetMapping("/pengguna/{idPengguna}/status/{status}")
    public List<TargetTabungan> getTargetTabungansByStatus(
            @PathVariable Integer idPengguna,
            @PathVariable String status) {
        return mTargetTabunganService.getTargetTabungansByStatus(idPengguna, status);
    }

    @PostMapping
    public ResponseEntity<Result> saveTargetTabungan(@RequestBody TargetTabungan targetTabunganParam) {
        try {
            TargetTabungan result = mTargetTabunganService.saveTargetTabungan(targetTabunganParam);
            return ResponseEntity.ok(new Result(200, "Target Tabungan berhasil disimpan", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Gagal menyimpan target tabungan: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Result> updateTargetTabungan(@RequestBody TargetTabungan targetTabunganParam) {
        boolean isSuccess = mTargetTabunganService.updateTargetTabungan(targetTabunganParam);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Target Tabungan berhasil diupdate"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Target Tabungan tidak ditemukan"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteTargetTabungan(@PathVariable Integer id) {
        boolean isSuccess = mTargetTabunganService.deleteTargetTabungan(id);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Target Tabungan berhasil dihapus"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Target Tabungan tidak ditemukan"));
        }
    }

    @PutMapping("/{id}/selesai")
    public ResponseEntity<Result> selesaikanTargetTabungan(@PathVariable Integer id) {
        TargetTabungan target = mTargetTabunganService.getTargetTabungan(id);
        if (target != null) {
            target.setStatus("selesai");
            mTargetTabunganService.updateTargetTabungan(target);
            return ResponseEntity.ok(new Result(200, "Target Tabungan berhasil diselesaikan"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Target Tabungan tidak ditemukan"));
        }
    }
}