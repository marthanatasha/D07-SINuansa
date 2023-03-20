package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Barista;

import java.util.Optional;

public interface BaristaDb extends JpaRepository<Barista, Long> {
    Optional<Barista> findById(Long id);
}
