package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.repository.InventoryDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
}
