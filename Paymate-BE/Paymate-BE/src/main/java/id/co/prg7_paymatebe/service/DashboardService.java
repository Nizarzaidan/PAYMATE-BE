package id.co.prg7_paymatebe.service;

import id.co.prg7_paymatebe.repository.TransaksiKeuanganJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private TransaksiKeuanganJpaRepository transaksiRepository;

    public Long getJumlahPengguna() {
        return transaksiRepository.countDistinctPengguna();
    }
}
