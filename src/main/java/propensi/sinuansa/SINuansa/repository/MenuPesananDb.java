package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.MenuPesanan;

import java.util.Optional;

public interface MenuPesananDb extends JpaRepository<MenuPesanan, Long> {
    Optional<MenuPesanan> findById(Long Id);
}
