package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.User;
import java.util.List;

public interface UserService {
    User findUserId(Long Id);
    List<User> getListUser();
    void addUser(User user);
}
