package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.StaffPabrik;

import java.util.Optional;

public interface StaffPabrikDb extends JpaRepository<StaffPabrik, Long>{
    Optional<StaffPabrik> findById(Long Id);
}
