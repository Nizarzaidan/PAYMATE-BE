package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.PenggunaJpaRepository;
import id.co.prg7_paymatebe.vo.Pengguna;
import id.co.prg7_paymatebe.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class RewardGamificationService {

    @Autowired
    private PenggunaJpaRepository penggunaRepository;

    // Konstanta untuk sistem reward
    private static final int POIN_PER_TABUNGAN_SELESAI = 30;
    private static final int POIN_PER_MEDALI = 300;

    /**
     * Tambah poin ketika user menyelesaikan tabungan
     */
    @Transactional
    public Result tambahPoinDariTabungan(Integer idPengguna) {
        try {
            Pengguna pengguna = penggunaRepository.findById(idPengguna).orElse(null);

            if (pengguna == null) {
                return new Result(404, "Pengguna tidak ditemukan");
            }

            // Tambah poin
            int poinLama = pengguna.getPoin();
            int poinBaru = poinLama + POIN_PER_TABUNGAN_SELESAI;
            pengguna.setPoin(poinBaru);

            // Cek apakah dapat medali baru
            int medaliLama = pengguna.getMedali();
            int medaliBaru = poinBaru / POIN_PER_MEDALI;

            boolean naikMedali = medaliBaru > medaliLama;

            if (naikMedali) {
                pengguna.setMedali(medaliBaru);
            }

            penggunaRepository.save(pengguna);

            // Prepare response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("poinBaru", poinBaru);
            responseData.put("medaliBaru", medaliBaru);
            responseData.put("naikMedali", naikMedali);
            responseData.put("poinDitambah", POIN_PER_TABUNGAN_SELESAI);

            String message = naikMedali
                    ? String.format("Selamat! +%d poin dan naik ke %d medali! 🎉", POIN_PER_TABUNGAN_SELESAI, medaliBaru)
                    : String.format("+%d poin! Total: %d poin", POIN_PER_TABUNGAN_SELESAI, poinBaru);

            return new Result(200, message, responseData);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(500, "Gagal menambah poin: " + e.getMessage());
        }
    }

    /**
     * Get reward info untuk user
     */
    public Result getRewardInfo(Integer idPengguna) {
        try {
            Pengguna pengguna = penggunaRepository.findById(idPengguna).orElse(null);

            if (pengguna == null) {
                return new Result(404, "Pengguna tidak ditemukan");
            }

            Map<String, Object> rewardData = new HashMap<>();
            rewardData.put("poin", pengguna.getPoin());
            rewardData.put("medali", pengguna.getMedali());
            rewardData.put("poinKeMedaliBerikutnya", POIN_PER_MEDALI - (pengguna.getPoin() % POIN_PER_MEDALI));
            rewardData.put("level", getLevelName(pengguna.getMedali()));

            return new Result(200, "Data reward berhasil diambil", rewardData);

        } catch (Exception e) {
            return new Result(500, "Gagal mengambil data reward: " + e.getMessage());
        }
    }

    /**
     * Get nama level berdasarkan jumlah medali
     */
    private String getLevelName(int medali) {
        if (medali >= 12) return "Berlian";
        if (medali >= 8) return "Platinum";
        if (medali >= 5) return "Emas";
        if (medali >= 3) return "Perak";
        if (medali >= 1) return "Perunggu";
        return "Pemula";
    }

    /**
     * Reset poin dan medali (untuk testing)
     */
    @Transactional
    public Result resetReward(Integer idPengguna) {
        try {
            Pengguna pengguna = penggunaRepository.findById(idPengguna).orElse(null);

            if (pengguna == null) {
                return new Result(404, "Pengguna tidak ditemukan");
            }

            pengguna.setPoin(0);
            pengguna.setMedali(0);
            penggunaRepository.save(pengguna);

            return new Result(200, "Reward berhasil direset");

        } catch (Exception e) {
            return new Result(500, "Gagal reset reward: " + e.getMessage());
        }
    }
}