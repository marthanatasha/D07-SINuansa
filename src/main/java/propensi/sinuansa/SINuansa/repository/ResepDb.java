package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Resep;

import java.util.Optional;

public interface ResepDb extends JpaRepository<Resep, Long> {
    Optional<Resep> findById(Long Id);

}
