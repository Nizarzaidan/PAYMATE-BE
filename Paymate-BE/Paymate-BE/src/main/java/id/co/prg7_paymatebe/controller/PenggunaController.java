package id.co.prg7_paymatebe.controller;

import id.co.prg7_paymatebe.service.PenggunaService;
import id.co.prg7_paymatebe.vo.Pengguna;
import id.co.prg7_paymatebe.vo.Result;
import id.co.prg7_paymatebe.security.JWTsecurity;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/pengguna")
public class PenggunaController {

    @Autowired
    private PenggunaService mPenggunaService;

    @GetMapping("/{id}")
    public Pengguna getPengguna(@PathVariable Integer id) {
        return mPenggunaService.getPengguna(id);
    }

    // Ambil seluruh data pengguna
    @GetMapping
    public List<Pengguna> getPenggunas() {
        return mPenggunaService.getPenggunas();
    }

    // Simpan data pengguna baru
    @PostMapping
    public Object savePengguna(HttpServletResponse response, @RequestBody Pengguna penggunaParam) {
        try {
            // Validasi email unik
            if (mPenggunaService.existsByEmail(penggunaParam.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Result(400, "Email sudah terdaftar"));
            }

            Pengguna result = mPenggunaService.savePengguna(penggunaParam);
            return ResponseEntity.ok(new Result(200, "Success", result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Fail: " + e.getMessage()));
        }
    }

    // Update data pengguna
    @PutMapping
    public Object updatePengguna(HttpServletResponse response, @RequestBody Pengguna penggunaParam) {
        boolean isSuccess = mPenggunaService.updatePengguna(penggunaParam);

        if (isSuccess) {
            return new Result(200, "Update Success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new Result(500, "Update Failed");
        }
    }

    // Hapus data pengguna (soft delete)
    @DeleteMapping("/{id}")
    public Object deletePengguna(@PathVariable("id") Integer id, HttpServletResponse response) {
        boolean isSuccess = mPenggunaService.deletePengguna(id);

        if (isSuccess) {
            return new Result(200, "Delete Success");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Result(404, "Pengguna Not Found");
        }
    }

    // Hapus permanen data pengguna
    @DeleteMapping("/permanent/{id}")
    public Object deletePermanentPengguna(@PathVariable("id") Integer id, HttpServletResponse response) {
        boolean isSuccess = mPenggunaService.deletePermanentPengguna(id);

        if (isSuccess) {
            return new Result(200, "Permanent Delete Success");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new Result(404, "Pengguna Not Found");
        }
    }

    // Cari pengguna berdasarkan email
    @GetMapping("/email/{email}")
    public Object getPenggunaByEmail(@PathVariable String email) {
        Pengguna pengguna = mPenggunaService.findByEmail(email);
        if (pengguna != null) {
            return ResponseEntity.ok(pengguna);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Result(404, "Pengguna dengan email " + email + " tidak ditemukan"));
        }
    }

    //untuk Login + JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Pengguna penggunaParam) {
        try {
            System.out.println(">>> Login request: email=" + penggunaParam.getEmail() + ", kataSandi=" + penggunaParam.getKataSandiHash());

            Pengguna pengguna = mPenggunaService.login(penggunaParam.getEmail(), penggunaParam.getKataSandiHash());

            if (pengguna == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new Result(401, "Email atau password salah", null));
            }

            String token = JWTsecurity.generateToken(pengguna.getEmail(), pengguna.getIdPengguna());
            System.out.println(">>> Token generated for user: " + pengguna.getEmail());

            //Bungkus response JSON
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", 200);
            responseBody.put("message", "Login berhasil");
            responseBody.put("token", token);
            responseBody.put("data", pengguna);

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(500, "Terjadi kesalahan: " + e.getMessage(), null));
        }
    }

}