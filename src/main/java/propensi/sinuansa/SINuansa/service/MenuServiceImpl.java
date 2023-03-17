package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Menu;
import propensi.sinuansa.SINuansa.repository.MenuDb;

import javax.transaction.Transactional;
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
}
