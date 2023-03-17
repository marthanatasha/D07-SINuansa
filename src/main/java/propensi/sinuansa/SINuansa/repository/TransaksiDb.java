package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Transaksi;

import java.util.Optional;

public interface TransaksiDb extends JpaRepository<Transaksi, Long> {
    Optional<Transaksi> findById(Long Id);

}
