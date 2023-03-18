package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.repository.UserDb;

import javax.transaction.Transactional;
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
    public List<UserModel> getListUser(){
        return userDb.findAll();
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
