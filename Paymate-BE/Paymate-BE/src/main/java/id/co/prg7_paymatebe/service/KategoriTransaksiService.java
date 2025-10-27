package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.KategoriTransaksiJpaRepository;
import id.co.prg7_paymatebe.vo.KategoriTransaksi;
import id.co.prg7_paymatebe.vo.Pengguna;
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

    // Method yang sudah ada...
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

    // Method baru untuk membuat kategori default
    public void createDefaultCategories(Pengguna pengguna) {
        // Cek apakah pengguna sudah memiliki kategori
        List<KategoriTransaksi> existingCategories = getKategoriTransaksisByPengguna(pengguna.getIdPengguna());
        if (!existingCategories.isEmpty()) {
            return; // Sudah ada kategori, tidak perlu buat default
        }

        // Data default untuk pengeluaran
        String[] pengeluaran = {"Makanan", "Transportasi", "Belanja", "Fashion", "Pendidikan", "Pulsa", "Air", "Listrik", "Pajak"};
        String[] warnaPengeluaran = {"#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#FECA57", "#FF9FF3", "#54A0FF", "#5F27CD", "#FF9F43"};
        String[] ikonPengeluaran = {"🍔", "🚗", "🛍️", "👕", "📚", "📱", "💧", "💡", "🏛️"};

        // Data default untuk pemasukan
        String[] pemasukan = {"Gaji", "Investasi", "Deposit"};
        String[] warnaPemasukan = {"#2ECC71", "#F1C40F", "#9B59B6"};
        String[] ikonPemasukan = {"💰", "📈", "🏦"};

        // Buat kategori pengeluaran
        for (int i = 0; i < pengeluaran.length; i++) {
            KategoriTransaksi kategori = new KategoriTransaksi();
            kategori.setPengguna(pengguna);
            kategori.setNamaKategori(pengeluaran[i]);
            kategori.setTipeKategori("pengeluaran");
            kategori.setWarna(warnaPengeluaran[i]);
            kategori.setIkon(ikonPengeluaran[i]);
            kategori.setStatus(true);
            kategori.setKeterangan("Kategori default pengeluaran");
            mKategoriTransaksiJpaRepository.save(kategori);
        }

        // Buat kategori pemasukan
        for (int i = 0; i < pemasukan.length; i++) {
            KategoriTransaksi kategori = new KategoriTransaksi();
            kategori.setPengguna(pengguna);
            kategori.setNamaKategori(pemasukan[i]);
            kategori.setTipeKategori("pemasukan");
            kategori.setWarna(warnaPemasukan[i]);
            kategori.setIkon(ikonPemasukan[i]);
            kategori.setStatus(true);
            kategori.setKeterangan("Kategori default pemasukan");
            mKategoriTransaksiJpaRepository.save(kategori);
        }
    }
}