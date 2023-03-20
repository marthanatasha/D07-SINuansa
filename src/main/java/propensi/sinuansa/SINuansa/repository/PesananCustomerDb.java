package propensi.sinuansa.SINuansa.repository;
<<<<<<< HEAD
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PesananCustomerDb extends JpaRepository <PesananCustomer,Long>{
    Optional<PesananCustomer> findById(Long id);
=======

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.sinuansa.SINuansa.model.PesananCustomer;

import java.util.Optional;

public interface PesananCustomerDb extends JpaRepository<PesananCustomer, Long> {
    Optional<PesananCustomer> findById(Long Id);
>>>>>>> 0a60da24272cca67ec0c1cfd52a48e1b442e25ad
}
