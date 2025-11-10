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

        // ===== REWARD: Inisialisasi poin & medali untuk user baru =====
        if (pengguna.getPoin() == null) {
            pengguna.setPoin(0);
        }
        if (pengguna.getMedali() == null) {
            pengguna.setMedali(0);
        }
        System.out.println(">>> User baru dengan poin: " + pengguna.getPoin() + ", medali: " + pengguna.getMedali());
        // ==============================================================

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
        if (StringUtils.hasLength(pengguna.getPeran())){
            result.setPeran(pengguna.getPeran());
        }
        if (pengguna.getStatusAktif() != null){
            result.setStatusAktif(pengguna.getStatusAktif());
        }
        if (StringUtils.hasLength(pengguna.getFotoProfil())){
            result.setFotoProfil(pengguna.getFotoProfil());
        }
        if (StringUtils.hasLength(pengguna.getNamaPanggilan())){
            result.setNamaPanggilan(pengguna.getNamaPanggilan());
        }
        if (StringUtils.hasLength(pengguna.getJenisKelamin())){
            result.setJenisKelamin(pengguna.getJenisKelamin());
        }
        if (pengguna.getTanggalLahir() != null){
            result.setTanggalLahir(pengguna.getTanggalLahir());
        }

        // ===== REWARD: Update poin & medali jika ada =====
        if (pengguna.getPoin() != null){
            result.setPoin(pengguna.getPoin());
        }
        if (pengguna.getMedali() != null){
            result.setMedali(pengguna.getMedali());
        }
        // ================================================

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

    //buat Login
    public Pengguna login(String email, String kataSandiHash) {
        System.out.println(">>> Login request: email=" + email + ", kataSandi=" + kataSandiHash);

        Pengguna pengguna = mPenggunaJpaRepository.findByEmail(email).orElse(null);

        if (pengguna == null) {
            System.out.println(">>> Tidak ditemukan pengguna dengan email: " + email);
            return null;
        }

        System.out.println(">>> Data DB: email=" + pengguna.getEmail() + ", hash=" + pengguna.getKataSandiHash());
        System.out.println(">>> Poin: " + pengguna.getPoin() + ", Medali: " + pengguna.getMedali());

        // Kalau kamu simpan password dalam plain text (bukan hash)
        if (pengguna.getKataSandiHash() != null && pengguna.getKataSandiHash().equals(kataSandiHash)) {
            System.out.println(">>> Password cocok!");
            return pengguna;
        }

        System.out.println(">>> Password tidak cocok!");
        return null;
    }
}