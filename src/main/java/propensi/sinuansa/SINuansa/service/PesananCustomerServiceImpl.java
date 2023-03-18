package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.repository.PesananCustomerDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PesananCustomerServiceImpl implements PesananCustomerService{
    @Autowired
    PesananCustomerDb pesananCustomerDb;

    @Override
    public PesananCustomer findPesananCustomerId(Long id){
        Optional<PesananCustomer> pesananCustomer= pesananCustomerDb.findById(id);
        if(pesananCustomer.isPresent()){
            return pesananCustomer.get();
        }else return null;
    }
}
