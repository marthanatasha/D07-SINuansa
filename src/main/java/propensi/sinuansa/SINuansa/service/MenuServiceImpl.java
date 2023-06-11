package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Menu;
import propensi.sinuansa.SINuansa.model.Resep;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.repository.MenuDb;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MenuServiceImpl implements MenuService{
    @Autowired
    MenuDb menuDb;

    @Override
    public Menu findMenuId(Long id){
        Optional<Menu> menu = menuDb.findById(id);
        if(menu.isPresent()){
            return menu.get();
        }else return null;
    }

    // ini udh gaperlu
    @Override
    public List<Menu> getListMenu(String cabang){
        List<Menu> listMenu = new ArrayList<>();
        for (Menu menu : getListMenuByCabangToHide(cabang)){
            if (menu.getStatus().equals(true)){
                listMenu.add(menu);
            }
        }
        return listMenu;
    }

    @Override
    public List<Menu> getAllMenu(String cabang){
        List<Menu> listMenu = new ArrayList<>();
        for(Menu menu : menuDb.findAll()){
            if(menu.getCabang().getNama().equals(cabang)){
                listMenu.add(menu);
            }
        }
       return listMenu;
    }

    @Override
    public  List<Menu> getListMenuByCabangToHide(String cabang){
        List<Menu> listMenu = new ArrayList<>();
        for (Menu menu : menuDb.findAll()){
            if (menu.getCabang().getNama().equals(cabang) && menu.getIsShow().equals(true)){
                listMenu.add(menu);
            }
        }
        return listMenu;
    }
    @Override
    public  List<Menu> getListMenuByCabangToShow(String cabang){
        List<Menu> listMenu = new ArrayList<>();
        for (Menu menu : menuDb.findAll()){
            if (menu.getCabang().getNama().equals(cabang) && menu.getIsShow().equals(false)){
                listMenu.add(menu);
            }
        }
        return listMenu;
    }

    @Override
    public void addMenu (Menu menu){
        menuDb.save(menu);
    }

    @Override
    public Boolean availabilityCheck(Menu menu){
        Boolean status = true;
        for (Resep resep : menu.getResepList()){
            if (resep.getInventory().getJumlah() < resep.getJumlah()){
                status = false;
                break;
            }
        }
        return status;
    }

    @Override
    public Menu updateMenu(Menu menu){
        menuDb.save(menu);
        return menu;
    }

    @Override
    public void hideMenu(Long[] ids){
        List<Menu> menu = menuDb.findByIdIn(ids);
        for (Menu hide : menu){
            hide.setIsShow(false);
        }
    }

    @Override
    public void showMenu(Long[] ids){
        List<Menu> menu = menuDb.findByIdIn(ids);
        for (Menu show : menu){
            show.setIsShow(true);
        }
    }


    @Override
    public Boolean canEdit(LocalTime currentTime){
        LocalTime opening = LocalTime.of(10,00,00);
        LocalTime closing = LocalTime.of(22,00,00);
        if (currentTime.compareTo(opening) <= 0 || currentTime.compareTo(closing) >= 0){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean canDelete(LocalTime currentTime){
        LocalTime opening = LocalTime.of(10,00,00);
        LocalTime closing = LocalTime.of(22,00,00);
        if (currentTime.compareTo(opening) <= 0 || currentTime.compareTo(closing) >= 0){
            return true;
        }
        else {
            return false;
        }
    }
}
