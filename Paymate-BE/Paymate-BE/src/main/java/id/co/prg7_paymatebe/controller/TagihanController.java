package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.TagihanService;
import id.co.prg7_paymatebe.vo.Tagihan;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/tagihan")
public class TagihanController {

    @Autowired
    private TagihanService mTagihanService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTagihan(@PathVariable Integer id) {
        Tagihan tagihan = mTagihanService.getTagihan(id);
        if (tagihan != null) {
            return ResponseEntity.ok(tagihan);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Tagihan tidak ditemukan"));
        }
    }

    @GetMapping("/pengguna/{idPengguna}")
    public List<Tagihan> getTagihansByPengguna(@PathVariable Integer idPengguna) {
        return mTagihanService.getTagihansByPengguna(idPengguna);
    }

    @GetMapping("/pengguna/{idPengguna}/status/{status}")
    public List<Tagihan> getTagihansByStatus(@PathVariable Integer idPengguna, @PathVariable String status) {
        return mTagihanService.getTagihansByStatus(idPengguna, status);
    }

    @PostMapping
    public ResponseEntity<Result> saveTagihan(@RequestBody Tagihan tagihanParam) {
        try {
            Tagihan result = mTagihanService.saveTagihan(tagihanParam);
            return ResponseEntity.ok(new Result(200, "Tagihan berhasil disimpan", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Gagal menyimpan tagihan: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Result> updateTagihan(@RequestBody Tagihan tagihanParam) {
        boolean isSuccess = mTagihanService.updateTagihan(tagihanParam);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Tagihan berhasil diupdate"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Tagihan tidak ditemukan"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteTagihan(@PathVariable Integer id) {
        boolean isSuccess = mTagihanService.deleteTagihan(id);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Tagihan berhasil dihapus"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Tagihan tidak ditemukan"));
        }
    }

    @PutMapping("/{id}/bayar")
    public ResponseEntity<Result> bayarTagihan(@PathVariable Integer id) {
        Tagihan tagihan = mTagihanService.getTagihan(id);
        if (tagihan != null) {
            tagihan.setStatus("Lunas");
            tagihan.setTerakhirDikirim(java.time.LocalDateTime.now());
            mTagihanService.updateTagihan(tagihan);
            return ResponseEntity.ok(new Result(200, "Tagihan berhasil dibayar"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Tagihan tidak ditemukan"));
        }
    }
}