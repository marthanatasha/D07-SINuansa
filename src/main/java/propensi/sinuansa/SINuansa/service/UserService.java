package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.UserModel;
import java.util.List;

public interface UserService {
    UserModel findUserId(Long Id);
    UserModel findByUsername(String username);
    List<UserModel> getListUser();
    UserModel addUser(UserModel user);
    String encrypt(String password);
    List<UserModel> getListUserByCabang(String cabang);
}
