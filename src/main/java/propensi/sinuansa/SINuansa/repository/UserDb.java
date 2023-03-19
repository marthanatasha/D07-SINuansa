package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.UserModel;

import java.util.Optional;

public interface UserDb extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findById(Long Id);
    UserModel findByUsername(String username);

}
