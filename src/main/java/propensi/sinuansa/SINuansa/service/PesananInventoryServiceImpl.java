package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.PesananInventory;
import propensi.sinuansa.SINuansa.repository.PesananInventoryDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PesananInventoryServiceImpl implements PesananInventoryService{
    @Autowired
    PesananInventoryDb pesananInventoryDb;

    @Override
    public PesananInventory findPesananInventoryId(Long id){
        Optional<PesananInventory> pesananInventory = pesananInventoryDb.findById(id);
        if(pesananInventory.isPresent()){
            return pesananInventory.get();
        }else return null;
    }
}
