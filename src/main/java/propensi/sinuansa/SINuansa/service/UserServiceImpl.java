package propensi.sinuansa.SINuansa.service;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Role;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.repository.UserDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDb userDb;

    @Override
    public UserModel findUserId(Long id){
        Optional<UserModel> user = userDb.findById(id);
        if(user.isPresent()){
            return user.get();
        }else return null;
    }

    @Override
    public UserModel findByUsername(String username){
        UserModel user = userDb.findByUsername(username);
        return user;
    }

    @Override
    public List<UserModel> getListUser(Role role, String cabang){
        List<UserModel> listUser = new ArrayList<>();
        if (role.equals(Role.ADMIN)){
            for (UserModel user : userDb.findAll()) {
                if (user.getRole().equals(Role.StaffPabrik)) {
                    listUser.add(user);
                } else {
                    if (checkCabang(user, cabang)){
                        listUser.add(user);
                    }
                }
            }
        }
        else if (role.equals(Role.MANAJER)){
            for (UserModel user : userDb.findAll()) {
                if (!user.getRole().equals(Role.StaffPabrik) && !(user.getRole().equals(Role.ADMIN))) {
                    if (checkCabang(user, cabang)) {
                        listUser.add(user);
                    }
                }
            }
        }
        return listUser;
    }

    private boolean checkCabang(UserModel user, String cabang){
        if (user.getCabang().getNama().equals(cabang)){
            return true;
        }
        return false;
    }

    @Override
    public UserModel addUser (UserModel user){
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

}
