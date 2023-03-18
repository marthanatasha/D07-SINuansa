package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.StaffInventory;
import propensi.sinuansa.SINuansa.repository.StaffInventoryDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class StaffInventoryServiceImpl implements StaffInventoryService{
    @Autowired
    StaffInventoryDb staffInventoryDb;

    @Override
    public StaffInventory findStaffInventoryId(Long id){
        Optional<StaffInventory> staffInventory = staffInventoryDb.findById(id);
        if(staffInventory.isPresent()){
            return staffInventory.get();
        }else return null;
    }
}
