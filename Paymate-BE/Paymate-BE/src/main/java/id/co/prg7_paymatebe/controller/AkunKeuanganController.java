package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.AkunKeuanganService;
import id.co.prg7_paymatebe.vo.AkunKeuangan;
import id.co.prg7_paymatebe.vo.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/akun-keuangan")
public class AkunKeuanganController {

    @Autowired
    private AkunKeuanganService mAkunKeuanganService;

    @GetMapping("/{id}")
    public AkunKeuangan getAkunKeuangan(@PathVariable Integer id) {
        return mAkunKeuanganService.getAkunKeuangan(id);
    }

    @GetMapping("/pengguna/{idPengguna}")
    public List<AkunKeuangan> getAkunKeuangansByPengguna(@PathVariable Integer idPengguna) {
        return mAkunKeuanganService.getAkunKeuangansByPengguna(idPengguna);
    }

    @GetMapping("/pengguna/{idPengguna}/aktif")
    public List<AkunKeuangan> getAkunKeuangansAktifByPengguna(@PathVariable Integer idPengguna) {
        return mAkunKeuanganService.getAkunKeuangansAktifByPengguna(idPengguna);
    }

    @PostMapping
    public Object saveAkunKeuangan(HttpServletResponse response, @RequestBody AkunKeuangan akunKeuanganParam) {
        try {
            AkunKeuangan result = mAkunKeuanganService.saveAkunKeuangan(akunKeuanganParam);
            return ResponseEntity.ok(new Result(200, "Success", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Fail: " + e.getMessage()));
        }
    }

    @PutMapping
    public Object updateAkunKeuangan(HttpServletResponse response, @RequestBody AkunKeuangan akunKeuanganParam) {
        boolean isSuccess = mAkunKeuanganService.updateAkunKeuangan(akunKeuanganParam);

        if (isSuccess) {
            return new Result(200, "Update Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Update Failed");
        }
    }

    @DeleteMapping("/{id}")
    public Object deleteAkunKeuangan(@PathVariable("id") Integer id, HttpServletResponse response) {
        boolean isSuccess = mAkunKeuanganService.deleteAkunKeuangan(id);

        if (isSuccess) {
            return new Result(200, "Delete Success");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Result(404, "Akun Keuangan Not Found");
        }
    }
}