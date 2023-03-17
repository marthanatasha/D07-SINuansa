package propensi.sinuansa.SINuansa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.sinuansa.SINuansa.model.Inventory;

import java.util.Optional;

@Repository
public interface InventoryDb extends JpaRepository <Inventory,Long>{
        Optional<Inventory> findById(Long id);
}
