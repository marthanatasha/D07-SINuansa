package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Menu;
import propensi.sinuansa.SINuansa.model.Resep;
import propensi.sinuansa.SINuansa.repository.MenuDb;

import javax.transaction.Transactional;
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

    @Override
    public List<Menu> getListMenu(){
        return menuDb.findAll();
    }

    @Override
    public void addMenu (Menu menu){
        menuDb.save(menu);
    }

    @Override
    public Boolean availabilityCheck(Menu menu){
        Boolean status = true;
        for (Resep resep : menu.getResepList()){
            System.out.println(resep.getInventory().getJumlah());
            if (resep.getInventory().getJumlah() < resep.getJumlah()){
                status = false;
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
}
