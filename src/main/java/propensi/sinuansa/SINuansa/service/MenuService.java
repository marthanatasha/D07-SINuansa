package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Menu;

import java.time.LocalTime;
import java.util.List;

public interface MenuService {
    Menu findMenuId(Long Id);
    List<Menu> getListMenu();
    void addMenu(Menu menu);
    Boolean availabilityCheck(Menu menu);
    Menu updateMenu (Menu menu);
    void hideMenu (Long[] ids);
    Boolean canEdit(LocalTime currentTime);
    Boolean canDelete(LocalTime currentTime);
    List<Menu> getListMenuByCabang(String cabang);

}
