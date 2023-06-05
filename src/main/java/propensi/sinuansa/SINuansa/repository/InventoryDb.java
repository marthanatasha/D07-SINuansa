package propensi.sinuansa.SINuansa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Inventory;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryDb extends JpaRepository <Inventory,Long>{
        Optional<Inventory> findById(Long id);
        Optional<Inventory> findByNama(String nama);

        @Query("SELECT a FROM Inventory a WHERE a.cabang = :cabang AND a.isKopi = false")
        List<Inventory> findAllByCabang(@Param("cabang") Cabang cabang);
}
