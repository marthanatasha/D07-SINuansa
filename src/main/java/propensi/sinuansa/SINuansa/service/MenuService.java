package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Menu;

import java.util.List;

public interface MenuService {
    Menu findMenuId(Long Id);
    List<Menu> getListMenu();
    void addMenu(Menu menu);
    Boolean availabilityCheck(Menu menu);

}
