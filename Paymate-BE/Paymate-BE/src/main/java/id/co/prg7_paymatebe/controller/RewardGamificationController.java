package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.RewardGamificationService;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reward-gamification")
public class RewardGamificationController {

    @Autowired
    private RewardGamificationService rewardGamificationService;

    /**
     * Endpoint untuk menambah poin ketika tabungan selesai
     * POST /api/reward-gamification/tambah-poin/{idPengguna}
     */
    @PostMapping("/tambah-poin/{idPengguna}")
    public ResponseEntity<Result> tambahPoin(@PathVariable Integer idPengguna) {
        Result result = rewardGamificationService.tambahPoinDariTabungan(idPengguna);

        if (result.getCode() == 200) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.valueOf(result.getCode())).body(result);
        }
    }

    /**
     * Get reward info untuk user
     * GET /api/reward-gamification/{idPengguna}
     */
    @GetMapping("/{idPengguna}")
    public ResponseEntity<Result> getRewardInfo(@PathVariable Integer idPengguna) {
        Result result = rewardGamificationService.getRewardInfo(idPengguna);

        if (result.getCode() == 200) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.valueOf(result.getCode())).body(result);
        }
    }

    /**
     * Reset reward (untuk testing)
     * DELETE /api/reward-gamification/reset/{idPengguna}
     */
    @DeleteMapping("/reset/{idPengguna}")
    public ResponseEntity<Result> resetReward(@PathVariable Integer idPengguna) {
        Result result = rewardGamificationService.resetReward(idPengguna);

        if (result.getCode() == 200) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.valueOf(result.getCode())).body(result);
        }
    }
}