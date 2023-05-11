package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierDb extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findById(Long Id);

    @Query("SELECT a FROM Supplier a WHERE a.cabang = :cabang")
    List<Supplier> findAllByCabang(@Param("cabang") Cabang cabang);

}
