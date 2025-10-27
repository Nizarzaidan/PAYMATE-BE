package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.AkunJpaRepository;
import id.co.prg7_paymatebe.repository.TransaksiKeuanganJpaRepository;
import id.co.prg7_paymatebe.repository.KategoriTransaksiJpaRepository;
import id.co.prg7_paymatebe.vo.TransaksiKeuangan;
import id.co.prg7_paymatebe.vo.RekapLaporan;
import id.co.prg7_paymatebe.vo.AkunKeuangan;
import id.co.prg7_paymatebe.vo.KategoriTransaksi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransaksiKeuanganService {

    @Autowired
    @Qualifier("TransaksiKeuanganJpaRepository")
    private TransaksiKeuanganJpaRepository mTransaksiKeuanganJpaRepository;

    @Autowired
    private AkunJpaRepository akunJpaRepository;

    @Autowired
    private KategoriTransaksiJpaRepository kategoriTransaksiJpaRepository;

    public TransaksiKeuangan getTransaksiKeuangan(Long id) {
        return mTransaksiKeuanganJpaRepository.findById(id).orElse(null);
    }

    public List<TransaksiKeuangan> getTransaksiKeuangansByPengguna(Integer idPengguna) {
        return mTransaksiKeuanganJpaRepository
                .findByPenggunaIdPenggunaOrderByTanggalTransaksiDesc(idPengguna);
    }

    public List<TransaksiKeuangan> getTransaksiKeuangansByTipe(Integer idPengguna, String tipeTransaksi) {
        return mTransaksiKeuanganJpaRepository
                .findByPenggunaIdPenggunaAndTipeTransaksiOrderByTanggalTransaksiDesc(idPengguna, tipeTransaksi);
    }

    public List<TransaksiKeuangan> getTransaksiKeuangansByPeriode(
            Integer idPengguna, LocalDateTime startDate, LocalDateTime endDate) {
        return mTransaksiKeuanganJpaRepository
                .findByPenggunaIdPenggunaAndTanggalTransaksiBetweenOrderByTanggalTransaksiDesc(
                        idPengguna, startDate, endDate);
    }

    public List<TransaksiKeuangan> getTransaksiKeuangansByBulanTahun(
            Integer idPengguna, Integer month, Integer year) {
        return mTransaksiKeuanganJpaRepository.findByPenggunaAndMonthYear(idPengguna, month, year);
    }

    public List<TransaksiKeuangan> getTransaksiKeuangansByAkun(Integer idPengguna, Integer idAkun) {
        return mTransaksiKeuanganJpaRepository
                .findByPenggunaIdPenggunaAndAkunIdAkunOrderByTanggalTransaksiDesc(idPengguna, idAkun);
    }

    public List<TransaksiKeuangan> getTransaksiKeuangansByKategori(Integer idPengguna, Integer idKategori) {
        return mTransaksiKeuanganJpaRepository
                .findByPenggunaIdPenggunaAndKategoriIdKategoriOrderByTanggalTransaksiDesc(idPengguna, idKategori);
    }

    public TransaksiKeuangan saveTransaksiKeuangan(TransaksiKeuangan transaksi) throws IOException {
        System.out.println(">>> Saving Transaksi Keuangan: " + transaksi);

        // Set timestamp
        transaksi.setDibuatPada(LocalDateTime.now());
        transaksi.setDiperbaruiPada(LocalDateTime.now());

        // Validasi akun
        AkunKeuangan akun = transaksi.getAkun();
        if (akun == null || akun.getIdAkun() == null) {
            throw new IOException("Akun keuangan harus dipilih!");
        }

        // Validasi kategori
        KategoriTransaksi kategori = transaksi.getKategori();
        if (kategori == null || kategori.getIdKategori() == null) {
            throw new IOException("Kategori transaksi harus dipilih!");
        }

        // Ambil akun dari database
        Optional<AkunKeuangan> akunDbOpt = akunJpaRepository.findById(akun.getIdAkun());
        if (!akunDbOpt.isPresent()) {
            throw new IOException("Akun dengan ID " + akun.getIdAkun() + " tidak ditemukan!");
        }
        akun = akunDbOpt.get();
        transaksi.setAkun(akun);

        // Ambil kategori dari database
        Optional<KategoriTransaksi> kategoriDbOpt = kategoriTransaksiJpaRepository.findById(kategori.getIdKategori());
        if (!kategoriDbOpt.isPresent()) {
            throw new IOException("Kategori dengan ID " + kategori.getIdKategori() + " tidak ditemukan!");
        }
        kategori = kategoriDbOpt.get();
        transaksi.setKategori(kategori);

        // Validasi tipe transaksi sesuai dengan kategori
        if (!transaksi.getTipeTransaksi().equalsIgnoreCase(kategori.getTipeKategori())) {
            throw new IOException("Tipe transaksi " + transaksi.getTipeTransaksi() +
                    " tidak sesuai dengan kategori " + kategori.getTipeKategori());
        }

        // Validasi nominal
        if (transaksi.getNominal() == null || transaksi.getNominal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IOException("Nominal harus lebih dari 0!");
        }

        // Simpan transaksi
        TransaksiKeuangan savedTransaksi = mTransaksiKeuanganJpaRepository.save(transaksi);

        // Update saldo akun
        updateSaldoAkun(akun, transaksi);

        return savedTransaksi;
    }

    private void updateSaldoAkun(AkunKeuangan akun, TransaksiKeuangan transaksi) {
        BigDecimal saldoSekarang = akun.getSaldo() != null ? akun.getSaldo() : BigDecimal.ZERO;
        BigDecimal nominal = transaksi.getNominal();

        if ("pemasukan".equalsIgnoreCase(transaksi.getTipeTransaksi())) {
            akun.setSaldo(saldoSekarang.add(nominal));
        } else if ("pengeluaran".equalsIgnoreCase(transaksi.getTipeTransaksi())) {
            akun.setSaldo(saldoSekarang.subtract(nominal));
        }

        akun.setDiperbaruiPada(LocalDateTime.now());
        akunJpaRepository.save(akun);
    }

    public boolean updateTransaksiKeuangan(TransaksiKeuangan transaksiKeuangan) {
        TransaksiKeuangan result = mTransaksiKeuanganJpaRepository
                .findById(transaksiKeuangan.getIdTransaksi())
                .orElse(null);
        if (result == null) return false;

        if (transaksiKeuangan.getAkun() != null) {
            result.setAkun(transaksiKeuangan.getAkun());
        }
        if (StringUtils.hasLength(transaksiKeuangan.getTipeTransaksi())) {
            result.setTipeTransaksi(transaksiKeuangan.getTipeTransaksi());
        }
        if (transaksiKeuangan.getKategori() != null) {
            result.setKategori(transaksiKeuangan.getKategori());
        }
        if (transaksiKeuangan.getNominal() != null) {
            result.setNominal(transaksiKeuangan.getNominal());
        }
        if (transaksiKeuangan.getTanggalTransaksi() != null) {
            result.setTanggalTransaksi(transaksiKeuangan.getTanggalTransaksi());
        }
        if (transaksiKeuangan.getCatatan() != null) {
            result.setCatatan(transaksiKeuangan.getCatatan());
        }

        result.setDiperbaruiPada(LocalDateTime.now());
        mTransaksiKeuanganJpaRepository.save(result);
        return true;
    }

    public boolean deleteTransaksiKeuangan(Long id) {
        TransaksiKeuangan result = mTransaksiKeuanganJpaRepository.findById(id).orElse(null);
        if (result == null) return false;

        mTransaksiKeuanganJpaRepository.delete(result);
        return true;
    }

    public List<RekapLaporan> getRekapLaporanByPengguna(Integer idPengguna) {
        return mTransaksiKeuanganJpaRepository.findRekapLaporanByPengguna(idPengguna);
    }

    public RekapLaporan getRekapLaporanByPenggunaAndPeriode(Integer idPengguna, String periode) {
        return mTransaksiKeuanganJpaRepository.findRekapLaporanByPenggunaAndPeriode(idPengguna, periode);
    }
}