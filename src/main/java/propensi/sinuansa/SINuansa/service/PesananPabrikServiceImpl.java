package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.TrueCondition;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.PesananInventory;
import propensi.sinuansa.SINuansa.repository.PesananInventoryDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PesananPabrikServiceImpl implements PesananPabrikService {
    @Autowired
    PesananInventoryDb pesananInventoryDb;


    @Override
    public PesananInventory findPesananInventoryId(Long id) {
        Optional<PesananInventory> pesananInventory = pesananInventoryDb.findById(id);
        if(pesananInventory.isPresent()){
            return pesananInventory.get();
        } else return null;
    }

    @Override
    public PesananInventory updatePesanan(PesananInventory pesananInventory) {
        pesananInventoryDb.save(pesananInventory);
        return pesananInventory;
    }

    @Override
    public List<PesananInventory> getListPesanan() {
        List<PesananInventory> res = new ArrayList<>();
        List<PesananInventory> data = pesananInventoryDb.findAll();

        for (PesananInventory pi : data) {
            if (pi.isKopi() && pi.getStatus().equals("On Process")){
                res.add(pi);
            }
        }
        return res;
    }

    @Override
    public PesananInventory updateStatusPesanan(PesananInventory pesananInventory) {
        pesananInventoryDb.save(pesananInventory);
        return pesananInventory;
    }
}
