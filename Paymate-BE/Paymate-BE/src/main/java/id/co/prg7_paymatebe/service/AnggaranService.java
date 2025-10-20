package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.AnggaranJpaRepository;
import id.co.prg7_paymatebe.vo.Anggaran;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class AnggaranService {

    @Qualifier("AnggaranJpaRepository")
    @Autowired
    private AnggaranJpaRepository mAnggaranJpaRepository;

    public Anggaran getAnggaran(Integer id) {
        return mAnggaranJpaRepository.findById(id).orElse(null);
    }

    public List<Anggaran> getAnggaransByPengguna(Integer idPengguna) {
        return mAnggaranJpaRepository.findByPenggunaIdPenggunaOrderByIdAnggaranAsc(idPengguna);
    }

    public List<Anggaran> getAnggaransAktifByPengguna(Integer idPengguna) {
        return mAnggaranJpaRepository.findByPenggunaIdPenggunaAndStatusOrderByPeriodeAwalAsc(idPengguna, "aktif");
    }

    public Anggaran saveAnggaran(Anggaran anggaran) {
        System.out.println(">>> Saving Anggaran: " + anggaran);
        return mAnggaranJpaRepository.save(anggaran);
    }

    public boolean updateAnggaran(Anggaran anggaran) {
        Anggaran result = mAnggaranJpaRepository.findById(anggaran.getIdAnggaran()).orElse(null);
        if (result == null) return false;

        if (StringUtils.hasLength(anggaran.getNamaAnggaran())) {
            result.setNamaAnggaran(anggaran.getNamaAnggaran());
        }
        if (StringUtils.hasLength(anggaran.getTipe())) {
            result.setTipe(anggaran.getTipe());
        }
        if (anggaran.getPeriodeAwal() != null) {
            result.setPeriodeAwal(anggaran.getPeriodeAwal());
        }
        if (anggaran.getPeriodeAkhir() != null) {
            result.setPeriodeAkhir(anggaran.getPeriodeAkhir());
        }
        if (anggaran.getNominalBatas() != null) {
            result.setNominalBatas(anggaran.getNominalBatas());
        }
        if (StringUtils.hasLength(anggaran.getCakupan())) {
            result.setCakupan(anggaran.getCakupan());
        }
        if (anggaran.getKategori() != null) {
            result.setKategori(anggaran.getKategori());
        }
        if (anggaran.getPersenPeringatan() != null) {
            result.setPersenPeringatan(anggaran.getPersenPeringatan());
        }
        if (StringUtils.hasLength(anggaran.getStatus())) {
            result.setStatus(anggaran.getStatus());
        }

        mAnggaranJpaRepository.save(result);
        return true;
    }

    public boolean deleteAnggaran(Integer id) {
        Anggaran result = mAnggaranJpaRepository.findById(id).orElse(null);
        if (result == null) return false;

        // Soft delete
        result.setStatus("nonaktif");
        mAnggaranJpaRepository.save(result);
        return true;
    }
}