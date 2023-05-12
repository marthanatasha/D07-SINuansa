package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Menu;

import java.time.LocalTime;
import java.util.List;

public interface MenuService {
    Menu findMenuId(Long Id);
    // getlistmenu udh gaperlu
    List<Menu> getListMenu(String cabang);
    void addMenu(Menu menu);
    Boolean availabilityCheck(Menu menu);
    Menu updateMenu (Menu menu);
    void hideMenu (Long[] ids);
    void showMenu(Long[] ids);
    Boolean canEdit(LocalTime currentTime);
    Boolean canDelete(LocalTime currentTime);
    List<Menu> getListMenuByCabangToHide(String cabang);
    List<Menu> getListMenuByCabangToShow(String cabang);

}
