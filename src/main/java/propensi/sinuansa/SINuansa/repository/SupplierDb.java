package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Supplier;

import java.util.Optional;

public interface SupplierDb extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findById(Long Id);

}
