package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.StaffPabrik;
import propensi.sinuansa.SINuansa.repository.StaffPabrikDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class StaffPabrikServiceImpl implements StaffPabrikService {
    @Autowired
    StaffPabrikDb staffPabrikDb;

    @Override
    public StaffPabrik findStaffPabrikId(Long id){
        Optional<StaffPabrik> staffPabrik = staffPabrikDb.findById(id);
        if(staffPabrik.isPresent()){
            return staffPabrik.get();
        }else return null;
    }

    @Override
    public void addStaff(StaffPabrik staff) {
        staffPabrikDb.save(staff);
    }
}
