package propensi.sinuansa.SINuansa.repository;
import propensi.sinuansa.SINuansa.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventoryDb extends JpaRepository <Inventory,Long>{
        Optional<Inventory> findById(Long id);
}
