package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.KategoriTransaksiJpaRepository;
import id.co.prg7_paymatebe.vo.KategoriTransaksi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class KategoriTransaksiService {

    @Qualifier("KategoriTransaksiJpaRepository")
    @Autowired
    private KategoriTransaksiJpaRepository mKategoriTransaksiJpaRepository;

    public KategoriTransaksi getKategoriTransaksi(Integer id) {
        return mKategoriTransaksiJpaRepository.findById(id).orElse(null);
    }

    public List<KategoriTransaksi> getKategoriTransaksisByPengguna(Integer idPengguna) {
        return mKategoriTransaksiJpaRepository.findByPenggunaIdPenggunaOrderByIdKategoriAsc(idPengguna);
    }

    public List<KategoriTransaksi> getKategoriTransaksisByTipe(Integer idPengguna, String tipeKategori) {
        return mKategoriTransaksiJpaRepository.findByPenggunaIdPenggunaAndTipeKategoriAndStatusTrueOrderByNamaKategoriAsc(idPengguna, tipeKategori);
    }

    public KategoriTransaksi saveKategoriTransaksi(KategoriTransaksi kategoriTransaksi) {
        System.out.println(">>> Saving Kategori Transaksi: " + kategoriTransaksi);
        return mKategoriTransaksiJpaRepository.save(kategoriTransaksi);
    }

    public boolean updateKategoriTransaksi(KategoriTransaksi kategoriTransaksi) {
        KategoriTransaksi result = mKategoriTransaksiJpaRepository.findById(kategoriTransaksi.getIdKategori()).orElse(null);
        if (result == null) return false;

        if (StringUtils.hasLength(kategoriTransaksi.getNamaKategori())) {
            result.setNamaKategori(kategoriTransaksi.getNamaKategori());
        }
        if (StringUtils.hasLength(kategoriTransaksi.getTipeKategori())) {
            result.setTipeKategori(kategoriTransaksi.getTipeKategori());
        }
        if (kategoriTransaksi.getInduk() != null) {
            result.setInduk(kategoriTransaksi.getInduk());
        }
        if (kategoriTransaksi.getWarna() != null) {
            result.setWarna(kategoriTransaksi.getWarna());
        }
        if (kategoriTransaksi.getIkon() != null) {
            result.setIkon(kategoriTransaksi.getIkon());
        }
        if (kategoriTransaksi.getStatus() != null) {
            result.setStatus(kategoriTransaksi.getStatus());
        }
        if (kategoriTransaksi.getKeterangan() != null) {
            result.setKeterangan(kategoriTransaksi.getKeterangan());
        }

        mKategoriTransaksiJpaRepository.save(result);
        return true;
    }

    public boolean deleteKategoriTransaksi(Integer id) {
        KategoriTransaksi result = mKategoriTransaksiJpaRepository.findById(id).orElse(null);
        if (result == null) return false;

        // Soft delete
        result.setStatus(false);
        mKategoriTransaksiJpaRepository.save(result);
        return true;
    }
}