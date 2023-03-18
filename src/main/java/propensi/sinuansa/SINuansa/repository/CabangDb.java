package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Cabang;

import java.util.Optional;

public interface CabangDb extends JpaRepository<Cabang, Long> {
    Optional<Cabang> findById(Long Id);
}
