package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.DashboardService;
import id.co.prg7_paymatebe.service.TransaksiKeuanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*") // agar bisa diakses dari React
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/jumlah-pengguna")
    public ResponseEntity<Long> getJumlahPengguna() {
        Long jumlah = dashboardService.getJumlahPengguna();
        return ResponseEntity.ok(jumlah);
    }
    @GetMapping("/total-uang-masuk")
    public ResponseEntity<Double> getTotalUangMasuk() {
        Double total = TransaksiKeuanganService.getTotalUangMasuk(); // ✅ panggil via instance
        return ResponseEntity.ok(total);
    }
}
