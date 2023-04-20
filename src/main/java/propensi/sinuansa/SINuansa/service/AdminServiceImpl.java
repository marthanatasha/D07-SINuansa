package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Admin;
import propensi.sinuansa.SINuansa.model.Manajer;
import propensi.sinuansa.SINuansa.repository.AdminDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminDb adminDb;

    @Override
    public Admin findAdminId(Long id){
        Optional<Admin> admin = adminDb.findById(id);
        if(admin.isPresent()){
            return admin.get();
        }else return null;
    }

    @Override
    public void addAdmin (Admin admin){
        adminDb.save(admin);
    }
}
