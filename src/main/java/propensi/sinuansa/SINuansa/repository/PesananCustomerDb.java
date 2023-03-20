package propensi.sinuansa.SINuansa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.PesananCustomer;

import java.util.Optional;

public interface PesananCustomerDb extends JpaRepository<PesananCustomer, Long> {
    Optional<PesananCustomer> findById(Long Id);
}
