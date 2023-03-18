package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.PesananInventory;

import java.util.Optional;

public interface PesananInventoryDb extends JpaRepository<PesananInventory, Long> {
    Optional<PesananInventory> findById(Long Id);
}
