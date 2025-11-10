package id.co.prg7_paymatebe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${upload.path:uploads/profile-photos}")
    private String uploadPath;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @PostMapping("/foto-profil")
    public ResponseEntity<?> uploadFotoProfil(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 400);
                response.put("message", "File tidak boleh kosong");
                return ResponseEntity.badRequest().body(response);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 400);
                response.put("message", "File harus berupa gambar");
                return ResponseEntity.badRequest().body(response);
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 400);
                response.put("message", "Ukuran file maksimal 5MB");
                return ResponseEntity.badRequest().body(response);
            }

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            Path filePath = Paths.get(uploadPath, newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = baseUrl + "/uploads/profile-photos/" + newFilename;

            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Upload berhasil");
            response.put("url", fileUrl);
            response.put("filename", newFilename);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("code", 500);
            response.put("message", "Gagal upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/foto-profil/{filename}")
    public ResponseEntity<?> deleteFotoProfil(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath, filename);
            File file = filePath.toFile();

            if (file.exists()) {
                if (file.delete()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("code", 200);
                    response.put("message", "File berhasil dihapus");
                    return ResponseEntity.ok(response);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("code", 404);
            response.put("message", "File tidak ditemukan");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("code", 500);
            response.put("message", "Gagal menghapus file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}