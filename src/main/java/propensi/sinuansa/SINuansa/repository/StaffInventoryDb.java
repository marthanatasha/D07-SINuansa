package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.StaffInventory;

import java.util.Optional;

public interface StaffInventoryDb extends JpaRepository<StaffInventory, Long> {
    Optional<StaffInventory> findById(Long Id);

}
