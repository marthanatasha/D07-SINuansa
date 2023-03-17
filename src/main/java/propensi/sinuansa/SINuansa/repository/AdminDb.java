package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.Admin;

import java.util.Optional;

public interface AdminDb extends JpaRepository<Admin, Long> {
    Optional<Admin> findById(Long Id);
}
