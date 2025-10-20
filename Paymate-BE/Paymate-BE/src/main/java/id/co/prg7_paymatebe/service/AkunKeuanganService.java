package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.AkunKeuanganJpaRepository;
import id.co.prg7_paymatebe.vo.AkunKeuangan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class AkunKeuanganService {

    @Qualifier("AkunKeuanganJpaRepository")
    @Autowired
    private AkunKeuanganJpaRepository mAkunKeuanganJpaRepository;

    public AkunKeuangan getAkunKeuangan(Integer id) {
        return mAkunKeuanganJpaRepository.findById(id).orElse(null);
    }

    public List<AkunKeuangan> getAkunKeuangansByPengguna(Integer idPengguna) {
        return mAkunKeuanganJpaRepository.findByPenggunaIdPenggunaOrderByIdAkunAsc(idPengguna);
    }

    public List<AkunKeuangan> getAkunKeuangansAktifByPengguna(Integer idPengguna) {
        return mAkunKeuanganJpaRepository.findByPenggunaIdPenggunaAndStatusTrueOrderByNamaAkunAsc(idPengguna);
    }

    public AkunKeuangan saveAkunKeuangan(AkunKeuangan akunKeuangan) {
        System.out.println(">>> Saving Akun Keuangan: " + akunKeuangan);
        return mAkunKeuanganJpaRepository.save(akunKeuangan);
    }

    public boolean updateAkunKeuangan(AkunKeuangan akunKeuangan) {
        AkunKeuangan result = mAkunKeuanganJpaRepository.findById(akunKeuangan.getIdAkun()).orElse(null);
        if (result == null) return false;

        if (StringUtils.hasLength(akunKeuangan.getNamaAkun())) {
            result.setNamaAkun(akunKeuangan.getNamaAkun());
        }
        if (StringUtils.hasLength(akunKeuangan.getJenisAkun())) {
            result.setJenisAkun(akunKeuangan.getJenisAkun());
        }
        if (akunKeuangan.getNomorAkun() != null) {
            result.setNomorAkun(akunKeuangan.getNomorAkun());
        }
        if (akunKeuangan.getSaldo() != null) {
            result.setSaldo(akunKeuangan.getSaldo());
        }
        if (StringUtils.hasLength(akunKeuangan.getMataUang())) {
            result.setMataUang(akunKeuangan.getMataUang());
        }
        if (akunKeuangan.getKeterangan() != null) {
            result.setKeterangan(akunKeuangan.getKeterangan());
        }
        if (akunKeuangan.getStatus() != null) {
            result.setStatus(akunKeuangan.getStatus());
        }

        mAkunKeuanganJpaRepository.save(result);
        return true;
    }

    public boolean deleteAkunKeuangan(Integer id) {
        AkunKeuangan result = mAkunKeuanganJpaRepository.findById(id).orElse(null);
        if (result == null) return false;

        // Soft delete
        result.setStatus(false);
        mAkunKeuanganJpaRepository.save(result);
        return true;
    }
}