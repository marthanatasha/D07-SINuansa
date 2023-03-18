package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Manajer;
import propensi.sinuansa.SINuansa.model.User;
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
    public User findUserId(Long id){
        Optional<User> user = userDb.findById(id);
        if(user.isPresent()){
            return user.get();
        }else return null;
    }

    @Override
    public List<User> getListUser(){
        return userDb.findAll();
    }

    @Override
    public void addUser (User user){
        userDb.save(user);
    }

}
