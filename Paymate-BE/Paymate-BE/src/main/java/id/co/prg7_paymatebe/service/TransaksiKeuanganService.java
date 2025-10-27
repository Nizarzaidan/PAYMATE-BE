package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.AkunJpaRepository;
import id.co.prg7_paymatebe.repository.TransaksiKeuanganJpaRepository;
import id.co.prg7_paymatebe.vo.TransaksiKeuangan;
import id.co.prg7_paymatebe.vo.RekapLaporan;
import id.co.prg7_paymatebe.vo.AkunKeuangan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransaksiKeuanganService {

    @Autowired
    @Qualifier("TransaksiKeuanganJpaRepository")
    private TransaksiKeuanganJpaRepository mTransaksiKeuanganJpaRepository;

    @Autowired
    private AkunJpaRepository akunJpaRepository;

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

        transaksi.setDibuatPada(LocalDateTime.now());
        transaksi.setDiperbaruiPada(LocalDateTime.now());

        AkunKeuangan akun = transaksi.getAkun();

        // ✅ Pastikan akun tidak null
        if (akun == null) {
            throw new IOException("Transaksi harus memiliki akun keuangan terkait!");
        }

        // ✅ Jika akun punya ID, ambil ulang dari DB untuk menghindari transient/null field
        if (akun.getIdAkun() != null) {
            Optional<AkunKeuangan> akunDbOpt = akunJpaRepository.findById(akun.getIdAkun());
            if (akunDbOpt.isPresent()) {
                akun = akunDbOpt.get();
                transaksi.setAkun(akun);
            } else {
                throw new IOException("AkunKeuangan dengan ID " + akun.getIdAkun() + " tidak ditemukan di database!");
            }
        } else {
            // ✅ Jika akun baru, pastikan namaAkun wajib diisi
            if (!StringUtils.hasText(akun.getNamaAkun())) {
                throw new IOException("Field 'namaAkun' wajib diisi untuk akun baru!");
            }
            if (akun.getPengguna() == null) {
                throw new IOException("Field 'pengguna' wajib diisi untuk akun baru!");
            }
        }

        // ✅ Simpan transaksi dulu
        TransaksiKeuangan saved = mTransaksiKeuanganJpaRepository.save(transaksi);

        // ✅ Update saldo akun
        BigDecimal saldoLama = akun.getSaldo() != null ? akun.getSaldo() : BigDecimal.ZERO;
        if ("pemasukan".equalsIgnoreCase(transaksi.getTipeTransaksi())) {
            akun.setSaldo(saldoLama.add(transaksi.getNominal()));
        } else if ("pengeluaran".equalsIgnoreCase(transaksi.getTipeTransaksi())) {
            akun.setSaldo(saldoLama.subtract(transaksi.getNominal()));
        }

        akun.setDiperbaruiPada(LocalDateTime.now());
        akunJpaRepository.save(akun);

        return saved;
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

    public List<RekapLaporan> getRekapLaporanByPengguna(Integer id_pengguna) {
        return mTransaksiKeuanganJpaRepository.findRekapLaporanByPengguna(id_pengguna);
    }

    public RekapLaporan getRekapLaporanByPenggunaAndPeriode(Integer idPengguna, String periode) {
        return mTransaksiKeuanganJpaRepository.findRekapLaporanByPenggunaAndPeriode(idPengguna, periode);
    }
    public Map<String, Object> getSummaryByMonthYear(int bulan, int tahun) {
        Object[] result = mTransaksiKeuanganJpaRepository.getTotalPemasukanPengeluaran(bulan, tahun);
        Double totalPemasukan = result[0] != null ? ((Number) result[0]).doubleValue() : 0.0;
        Double totalPengeluaran = result[1] != null ? ((Number) result[1]).doubleValue() : 0.0;

        Map<String, Object> data = new HashMap<>();
        data.put("totalPemasukan", totalPemasukan);
        data.put("totalPengeluaran", totalPengeluaran);
        data.put("saldoBersih", totalPemasukan - totalPengeluaran);
        return data;
    }
}
