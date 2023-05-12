package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import propensi.sinuansa.SINuansa.model.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuDb extends JpaRepository<Menu, Long> {
    Optional<Menu> findById(Long Id);
    public List<Menu> findByIdIn(Long[] id);

}
