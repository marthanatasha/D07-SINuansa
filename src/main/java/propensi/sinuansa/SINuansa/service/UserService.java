package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.UserModel;
import java.util.List;

public interface UserService {
    UserModel findUserId(Long Id);
    UserModel findByUsername(String username);
    List<UserModel> getListUser();
    UserModel addUser(UserModel user);
    public String encrypt(String password);
}
