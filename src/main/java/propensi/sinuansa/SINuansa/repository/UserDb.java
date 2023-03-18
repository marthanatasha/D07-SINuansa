package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.User;

import java.util.Optional;

public interface UserDb extends JpaRepository<User, Long> {
    Optional<User> findById(Long Id);

}
