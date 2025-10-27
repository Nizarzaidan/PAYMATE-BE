package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.TagihanJpaRepository;
import id.co.prg7_paymatebe.repository.PenggunaJpaRepository;
import id.co.prg7_paymatebe.vo.Pengguna;
import id.co.prg7_paymatebe.vo.Tagihan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TagihanService {

    @Qualifier("TagihanJpaRepository")
    @Autowired
    private TagihanJpaRepository mTagihanJpaRepository;

    @Autowired
    private PenggunaJpaRepository penggunaJpaRepository;

    // -----------------------------
    // 🔹 Ambil 1 Tagihan berdasarkan ID
    // -----------------------------
    public Tagihan getTagihan(Integer id) {
        return mTagihanJpaRepository.findById(id).orElse(null);
    }

    // -----------------------------
    // 🔹 Ambil daftar Tagihan berdasarkan Pengguna
    // -----------------------------
    public List<Tagihan> getTagihansByPengguna(Integer idPengguna) {
        return mTagihanJpaRepository.findByPenggunaIdPenggunaOrderByIdTagihanAsc(idPengguna);
    }

    // -----------------------------
    // 🔹 Ambil Tagihan berdasarkan Status
    // -----------------------------
    public List<Tagihan> getTagihansByStatus(Integer idPengguna, String status) {
        if (status.equalsIgnoreCase("all")) {
            return mTagihanJpaRepository.findByPenggunaIdPenggunaOrderByIdTagihanAsc(idPengguna);
        }
        return mTagihanJpaRepository.findByPenggunaIdPenggunaAndStatusOrderByTanggalJatuhTempoAsc(idPengguna, status);
    }

    // -----------------------------
    // 🔹 Simpan Tagihan baru
    // -----------------------------
    public Tagihan saveTagihan(Tagihan tagihan) {
        try {
            System.out.println(">>> Saving Tagihan:");
            System.out.println("Pengguna: " + (tagihan.getPengguna() != null ? tagihan.getPengguna().getIdPengguna() : "NULL"));
            System.out.println("Nama Tagihan: " + tagihan.getNamaTagihan());
            System.out.println("Nominal: " + tagihan.getNominal());

            // Validasi pengguna
            if (tagihan.getPengguna() == null || tagihan.getPengguna().getIdPengguna() == null) {
                throw new IllegalArgumentException("ID Pengguna tidak boleh null!");
            }

            Optional<Pengguna> penggunaOpt = penggunaJpaRepository.findById(tagihan.getPengguna().getIdPengguna());
            if (penggunaOpt.isEmpty()) {
                throw new IllegalArgumentException("Pengguna dengan ID " + tagihan.getPengguna().getIdPengguna() + " tidak ditemukan!");
            }

            tagihan.setPengguna(penggunaOpt.get());

            // 🔹 Default status "Belum Lunas"
            if (!StringUtils.hasLength(tagihan.getStatus())) {
                tagihan.setStatus("Belum Lunas");
            } else {
                String status = tagihan.getStatus().trim();
                if (status.equalsIgnoreCase("Lunas") || status.equalsIgnoreCase("Belum Lunas")) {
                    tagihan.setStatus(status);
                } else {
                    tagihan.setStatus("Belum Lunas");
                }
            }

            // 🔹 Normalisasi tipe perulangan
            if (StringUtils.hasLength(tagihan.getTipePerulangan())) {
                String tipe = tagihan.getTipePerulangan().trim().toLowerCase();
                switch (tipe) {
                    case "tidak_berulang":
                    case "tahunan":
                    case "bulanan":
                    case "mingguan":
                    case "harian":
                        tagihan.setTipePerulangan(tipe);
                        break;
                    default:
                        tagihan.setTipePerulangan("tidak_berulang");
                        break;
                }
            } else {
                tagihan.setTipePerulangan("tidak_berulang");
            }

            System.out.println("Status final: " + tagihan.getStatus());
            System.out.println("Tipe Perulangan final: " + tagihan.getTipePerulangan());

            return mTagihanJpaRepository.save(tagihan);

        } catch (Exception e) {
            System.err.println("❌ Error saat simpan Tagihan: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // -----------------------------
    // 🔹 Update Tagihan
    // -----------------------------
    public boolean updateTagihan(Tagihan tagihan) {
        try {
            Tagihan result = mTagihanJpaRepository.findById(tagihan.getIdTagihan()).orElse(null);
            if (result == null) return false;

            if (StringUtils.hasLength(tagihan.getNamaTagihan())) {
                result.setNamaTagihan(tagihan.getNamaTagihan());
            }
            if (tagihan.getNominal() != null) {
                result.setNominal(tagihan.getNominal());
            }
            if (tagihan.getTanggalJatuhTempo() != null) {
                result.setTanggalJatuhTempo(tagihan.getTanggalJatuhTempo());
            }
            if (StringUtils.hasLength(tagihan.getTipePerulangan())) {
                String tipe = tagihan.getTipePerulangan().trim().toLowerCase();
                switch (tipe) {
                    case "tidak_berulang":
                    case "tahunan":
                    case "bulanan":
                    case "mingguan":
                    case "harian":
                        result.setTipePerulangan(tipe);
                        break;
                    default:
                        result.setTipePerulangan("tidak_berulang");
                        break;
                }
            }
            if (tagihan.getTerakhirDikirim() != null) {
                result.setTerakhirDikirim(tagihan.getTerakhirDikirim());
            }

            if (StringUtils.hasLength(tagihan.getStatus())) {
                String status = tagihan.getStatus().trim();
                if (status.equalsIgnoreCase("Lunas") || status.equalsIgnoreCase("Belum Lunas")) {
                    result.setStatus(status);
                } else {
                    result.setStatus("Belum Lunas");
                }
            }

            mTagihanJpaRepository.save(result);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Error saat update Tagihan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // 🔹 Hapus Tagihan
    // -----------------------------
    public boolean deleteTagihan(Integer id) {
        try {
            Tagihan result = mTagihanJpaRepository.findById(id).orElse(null);
            if (result == null) return false;

            mTagihanJpaRepository.delete(result);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Error saat hapus Tagihan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}