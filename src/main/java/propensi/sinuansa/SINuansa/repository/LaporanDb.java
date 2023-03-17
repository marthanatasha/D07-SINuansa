package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Laporan;

import java.util.Optional;

public interface LaporanDb extends JpaRepository<Laporan, Long> {
    Optional<Laporan> findById(Long Id);
}
