package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.RewardService;
import id.co.prg7_paymatebe.vo.Reward;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reward")
public class RewardController {

    @Autowired
    private RewardService mRewardService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReward(@PathVariable Integer id) {
        Reward reward = mRewardService.getReward(id);
        if (reward != null) {
            return ResponseEntity.ok(reward);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Reward tidak ditemukan"));
        }
    }

    @GetMapping
    public List<Reward> getRewards() {
        return mRewardService.getRewards();
    }

    @GetMapping("/poin/max/{maxPoin}")
    public List<Reward> getRewardsByPoinMax(@PathVariable Integer maxPoin) {
        return mRewardService.getRewardsByPoinMax(maxPoin);
    }

    @GetMapping("/poin/min/{minPoin}")
    public List<Reward> getRewardsByPoinMin(@PathVariable Integer minPoin) {
        return mRewardService.getRewardsByPoinMin(minPoin);
    }

    @PostMapping
    public ResponseEntity<Result> saveReward(@RequestBody Reward rewardParam) {
        try {
            Reward result = mRewardService.saveReward(rewardParam);
            return ResponseEntity.ok(new Result(200, "Reward berhasil disimpan", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Gagal menyimpan reward: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Result> updateReward(@RequestBody Reward rewardParam) {
        boolean isSuccess = mRewardService.updateReward(rewardParam);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Reward berhasil diupdate"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Reward tidak ditemukan"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteReward(@PathVariable Integer id) {
        boolean isSuccess = mRewardService.deleteReward(id);

        if (isSuccess) {
            return ResponseEntity.ok(new Result(200, "Reward berhasil dihapus"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Reward tidak ditemukan"));
        }
    }
}