package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.PenggunaJpaRepository;
import id.co.prg7_paymatebe.vo.Pengguna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PenggunaService {

    @Qualifier("PenggunaJpaRepository")
    @Autowired
    private PenggunaJpaRepository mPenggunaJpaRepository;

    // Ambil pengguna berdasarkan ID
    public Pengguna getPengguna(Integer id){
        return mPenggunaJpaRepository.findById(id).orElse(null);
    }

    // Ambil semua pengguna
    public List<Pengguna> getPenggunas(){
        return mPenggunaJpaRepository.findAllByOrderByIdPenggunaAsc();
    }

    // Simpan pengguna baru
    public Pengguna savePengguna(Pengguna pengguna) {
        System.out.println(">>> Saving Pengguna: " + pengguna);

        // Set nilai default
        if (pengguna.getTanggalDaftar() == null) {
            pengguna.setTanggalDaftar(LocalDateTime.now());
        }
        if (!StringUtils.hasLength(pengguna.getPeran())) {
            pengguna.setPeran("user");
        }
        if (pengguna.getStatusAktif() == null) {
            pengguna.setStatusAktif(true);
        }
        if (!StringUtils.hasLength(pengguna.getMataUang())) {
            pengguna.setMataUang("IDR");
        }
        if (!StringUtils.hasLength(pengguna.getBahasa())) {
            pengguna.setBahasa("id");
        }

        return mPenggunaJpaRepository.save(pengguna);
    }

    // Update data pengguna
    public boolean updatePengguna(Pengguna pengguna){
        Pengguna result = mPenggunaJpaRepository.findById(pengguna.getIdPengguna()).orElse(null);

        if (result == null){
            return false;
        }

        if (StringUtils.hasLength(pengguna.getEmail())){
            result.setEmail(pengguna.getEmail());
        }
        if (StringUtils.hasLength(pengguna.getKataSandiHash())){
            result.setKataSandiHash(pengguna.getKataSandiHash());
        }
        if (StringUtils.hasLength(pengguna.getNamaLengkap())){
            result.setNamaLengkap(pengguna.getNamaLengkap());
        }
        if (StringUtils.hasLength(pengguna.getTelepon())){
            result.setTelepon(pengguna.getTelepon());
        }
        if (pengguna.getZonaWaktu() != null){
            result.setZonaWaktu(pengguna.getZonaWaktu());
        }
        if (StringUtils.hasLength(pengguna.getPeran())){
            result.setPeran(pengguna.getPeran());
        }
        if (pengguna.getStatusAktif() != null){
            result.setStatusAktif(pengguna.getStatusAktif());
        }
        if (StringUtils.hasLength(pengguna.getMataUang())){
            result.setMataUang(pengguna.getMataUang());
        }
        if (StringUtils.hasLength(pengguna.getBahasa())){
            result.setBahasa(pengguna.getBahasa());
        }
        if (pengguna.getFotoProfil() != null){
            result.setFotoProfil(pengguna.getFotoProfil());
        }
        if (pengguna.getPreferensiNotifikasi() != null){
            result.setPreferensiNotifikasi(pengguna.getPreferensiNotifikasi());
        }

        mPenggunaJpaRepository.save(result);
        return true;
    }

    // Hapus data pengguna (soft delete dengan mengubah status)
    public boolean deletePengguna(Integer id){
        Pengguna result = mPenggunaJpaRepository.findById(id).orElse(null);

        if (result == null){
            return false;
        }

        // Soft delete dengan mengubah status aktif
        result.setStatusAktif(false);
        mPenggunaJpaRepository.save(result);
        return true;
    }

    // Hapus permanen data pengguna
    public boolean deletePermanentPengguna(Integer id){
        Pengguna result = mPenggunaJpaRepository.findById(id).orElse(null);

        if (result == null){
            return false;
        }

        mPenggunaJpaRepository.delete(result);
        return true;
    }

    // Cari pengguna berdasarkan email
    public Pengguna findByEmail(String email) {
        return mPenggunaJpaRepository.findByEmail(email).orElse(null);
    }

    // Cek apakah email sudah terdaftar
    public boolean existsByEmail(String email) {
        return mPenggunaJpaRepository.existsByEmail(email);
    }
}