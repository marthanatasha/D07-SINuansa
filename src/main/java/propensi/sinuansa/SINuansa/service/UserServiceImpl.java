package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.User;
import propensi.sinuansa.SINuansa.repository.UserDb;

import javax.transaction.Transactional;
import java.util.Optional;

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
}
