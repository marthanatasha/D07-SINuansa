package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.repository.InventoryDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    InventoryDb inventoryDb;

    @Override
    public List<Inventory> getListInventory(){

        return inventoryDb.findAll();
    }

    @Override
    public void addInventory(Inventory inventory){
        inventoryDb.save(inventory);
    }

    @Override
    public Inventory updateInventory(Inventory inventory){
        inventoryDb.save(inventory);
        return inventory;
    }

    @Override
    public Inventory deleteInventory(Inventory inventory){
        inventoryDb.delete(inventory);
        return inventory;
    }

    @Override
    public Inventory getInventoryById(Long id){
        Optional<Inventory> inventory = inventoryDb.findById(id);
        if(inventory.isPresent()){
            return inventory.get();
        }else return null;
    }

    @Override
    public Inventory getInventoryByNama(String nama){
        Optional<Inventory> inventory = inventoryDb.findByNama(nama);
        if(inventory.isPresent()){
            return inventory.get();
        }else return null;
    }

    @Override
    public List<Inventory> getListInventoryBasedOnType(boolean isKopi) {
        List<Inventory> res = new ArrayList<>();
        List<Inventory> exists = getListInventory();

        for (Inventory i : exists) {
            if (i.isKopi() == isKopi) {
                res.add(i);
            }
        }

        return res;
    }

    @Override
    public List<Inventory> getListInventoryByCabang(String cabang){
        List<Inventory> res = new ArrayList<>();

        for (Inventory i : inventoryDb.findAll()) {
            if (i.getCabang().getNama().equals(cabang)) {
                res.add(i);
            }
        }
        return res;
    }
}
