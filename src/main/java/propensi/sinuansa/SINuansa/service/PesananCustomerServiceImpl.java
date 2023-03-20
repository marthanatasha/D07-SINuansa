package propensi.sinuansa.SINuansa.service;

<<<<<<< HEAD
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.repository.PesananCustomerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.repository.PesananCustomerDb;

import javax.transaction.Transactional;
>>>>>>> 0a60da24272cca67ec0c1cfd52a48e1b442e25ad
import java.util.Optional;

@Service
@Transactional
<<<<<<< HEAD

public class PesananCustomerServiceImpl implements PesananCustomerService{

=======
public class PesananCustomerServiceImpl implements PesananCustomerService{
>>>>>>> 0a60da24272cca67ec0c1cfd52a48e1b442e25ad
    @Autowired
    PesananCustomerDb pesananCustomerDb;

    @Override
<<<<<<< HEAD
    public void addPesananCustomer (PesananCustomer pesananCustomer){
        pesananCustomerDb.save(pesananCustomer);
    }

    @Override
    public PesananCustomer getPesananCustomerById (Long id){
        Optional<PesananCustomer> pesananCustomer = pesananCustomerDb.findById(id);
=======
    public PesananCustomer findPesananCustomerId(Long id){
        Optional<PesananCustomer> pesananCustomer= pesananCustomerDb.findById(id);
>>>>>>> 0a60da24272cca67ec0c1cfd52a48e1b442e25ad
        if(pesananCustomer.isPresent()){
            return pesananCustomer.get();
        }else return null;
    }
}
