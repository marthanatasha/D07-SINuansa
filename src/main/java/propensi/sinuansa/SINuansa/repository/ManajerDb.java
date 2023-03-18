package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Manajer;

import java.util.Optional;

public interface ManajerDb extends JpaRepository<Manajer, Long> {
    Optional<Manajer> findById(Long Id);

}
