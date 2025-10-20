package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.TargetTabunganJpaRepository;
import id.co.prg7_paymatebe.vo.TargetTabungan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    public TargetTabungan saveTargetTabungan(TargetTabungan targetTabungan) {
        System.out.println(">>> Saving Target Tabungan: " + targetTabungan);
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

        mTargetTabunganJpaRepository.save(result);
        return true;
    }

    public boolean deleteTargetTabungan(Integer id) {
        TargetTabungan result = mTargetTabunganJpaRepository.findById(id).orElse(null);
        if (result == null) return false;

        mTargetTabunganJpaRepository.delete(result);
        return true;
    }
}