package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Pembayaran;

import java.util.Optional;

public interface PembayaranDb extends JpaRepository<Pembayaran, Long> {
    Optional<Pembayaran> findById(Long id);
}
