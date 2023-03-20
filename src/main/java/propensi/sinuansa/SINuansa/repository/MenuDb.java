package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Menu;

import java.util.Optional;

public interface MenuDb extends JpaRepository<Menu, Long> {
    Optional<Menu> findById(Long Id);
}
