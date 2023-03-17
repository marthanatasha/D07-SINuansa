package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Admin;
import propensi.sinuansa.SINuansa.repository.AdminDb;

import java.util.Optional;

@Service
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
}
