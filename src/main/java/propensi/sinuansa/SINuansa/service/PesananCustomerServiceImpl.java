package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.repository.PesananCustomerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class PesananCustomerServiceImpl implements PesananCustomerService{

    @Autowired
    PesananCustomerDb pesananCustomerDb;

    @Override
    public void addPesananCustomer (PesananCustomer pesananCustomer){
        pesananCustomerDb.save(pesananCustomer);
    }

    @Override
    public PesananCustomer getPesananCustomerById (Long id){
        Optional<PesananCustomer> pesananCustomer = pesananCustomerDb.findById(id);
        if(pesananCustomer.isPresent()){
            return pesananCustomer.get();
        }else return null;
    }
}
