package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.TargetTabunganJpaRepository;
import id.co.prg7_paymatebe.vo.TargetTabungan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TargetTabunganService {

    @Qualifier("TargetTabunganJpaRepository")
    @Autowired
    private TargetTabunganJpaRepository mTargetTabunganJpaRepository;

    public TargetTabungan getTargetTabungan(Integer id) {
        return mTargetTabunganJpaRepository.findById(id).orElse(null);
    }

    public List<TargetTabungan> getTargetTabungansByPengguna(Integer idPengguna) {
        return mTargetTabunganJpaRepository.findByPenggunaIdPenggunaOrderByIdTargetAsc(idPengguna);
    }

    public List<TargetTabungan> getTargetTabungansByStatus(Integer idPengguna, String status) {
        return mTargetTabunganJpaRepository.findByPenggunaIdPenggunaAndStatusOrderByTanggalMulaiAsc(idPengguna, status);
    }

    public List<TargetTabungan> getTargetAktifByPengguna(Integer idPengguna) {
        return mTargetTabunganJpaRepository.findTargetAktifByPengguna(idPengguna);
    }

    public TargetTabungan saveTargetTabungan(TargetTabungan targetTabungan) {
        System.out.println(">>> Saving Target Tabungan: " + targetTabungan);

        // Set default values jika null
        if (targetTabungan.getNominalSekarang() == null) {
            targetTabungan.setNominalSekarang(BigDecimal.ZERO);
        }
        if (targetTabungan.getMataUang() == null) {
            targetTabungan.setMataUang("IDR");
        }
        if (targetTabungan.getStatus() == null) {
            targetTabungan.setStatus("berjalan");
        }

        return mTargetTabunganJpaRepository.save(targetTabungan);
    }

    public boolean updateTargetTabungan(TargetTabungan targetTabungan) {
        TargetTabungan result = mTargetTabunganJpaRepository.findById(targetTabungan.getIdTarget()).orElse(null);
        if (result == null) return false;

        if (StringUtils.hasLength(targetTabungan.getNamaTarget())) {
            result.setNamaTarget(targetTabungan.getNamaTarget());
        }
        if (targetTabungan.getTargetNominal() != null) {
            result.setTargetNominal(targetTabungan.getTargetNominal());
        }
        if (targetTabungan.getNominalSekarang() != null) {
            result.setNominalSekarang(targetTabungan.getNominalSekarang());
        }
        if (StringUtils.hasLength(targetTabungan.getMataUang())) {
            result.setMataUang(targetTabungan.getMataUang());
        }
        if (StringUtils.hasLength(targetTabungan.getFrekuensiPengisian())) {
            result.setFrekuensiPengisian(targetTabungan.getFrekuensiPengisian());
        }
        if (targetTabungan.getNominalPengisian() != null) {
            result.setNominalPengisian(targetTabungan.getNominalPengisian());
        }
        if (targetTabungan.getTanggalMulai() != null) {
            result.setTanggalMulai(targetTabungan.getTanggalMulai());
        }
        if (targetTabungan.getTanggalSelesai() != null) {
            result.setTanggalSelesai(targetTabungan.getTanggalSelesai());
        }
        if (StringUtils.hasLength(targetTabungan.getStatus())) {
            result.setStatus(targetTabungan.getStatus());
        }
        if (targetTabungan.getCatatan() != null) {
            result.setCatatan(targetTabungan.getCatatan());
        }
        if (targetTabungan.getFotoTabungan() != null) {
            result.setFotoTabungan(targetTabungan.getFotoTabungan());
        }

        mTargetTabunganJpaRepository.save(result);
        return true;
    }

    public boolean tambahNominalTabungan(Integer idTarget, BigDecimal nominal) {
        TargetTabungan target = mTargetTabunganJpaRepository.findById(idTarget).orElse(null);
        if (target == null) return false;

        BigDecimal nominalSekarang = target.getNominalSekarang().add(nominal);
        target.setNominalSekarang(nominalSekarang);

        // Cek apakah target sudah tercapai
        if (nominalSekarang.compareTo(target.getTargetNominal()) >= 0) {
            target.setStatus("selesai");
        }

        mTargetTabunganJpaRepository.save(target);
        return true;
    }

    public boolean kurangiNominalTabungan(Integer idTarget, BigDecimal nominal) {
        TargetTabungan target = mTargetTabunganJpaRepository.findById(idTarget).orElse(null);
        if (target == null) return false;

        BigDecimal nominalSekarang = target.getNominalSekarang().subtract(nominal);
        if (nominalSekarang.compareTo(BigDecimal.ZERO) < 0) {
            nominalSekarang = BigDecimal.ZERO;
        }
        target.setNominalSekarang(nominalSekarang);

        // Jika nominal berkurang, status kembali ke berjalan
        if (nominalSekarang.compareTo(target.getTargetNominal()) < 0) {
            target.setStatus("berjalan");
        }

        mTargetTabunganJpaRepository.save(target);
        return true;
    }

    public boolean deleteTargetTabungan(Integer id) {
        TargetTabungan result = mTargetTabunganJpaRepository.findById(id).orElse(null);
        if (result == null) return false;

        mTargetTabunganJpaRepository.delete(result);
        return true;
    }

    public BigDecimal getTotalTabunganByPengguna(Integer idPengguna) {
        List<TargetTabungan> targets = mTargetTabunganJpaRepository.findByPenggunaIdPenggunaOrderByIdTargetAsc(idPengguna);
        return targets.stream()
                .map(TargetTabungan::getNominalSekarang)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
