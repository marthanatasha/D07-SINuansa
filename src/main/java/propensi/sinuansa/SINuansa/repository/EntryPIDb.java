package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.EntryPI;

import java.util.Optional;

public interface EntryPIDb extends JpaRepository<EntryPI, Long> {
    Optional<EntryPI> findById(Long Id);
}
