package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.MenuPesanan;
import propensi.sinuansa.SINuansa.repository.MenuPesananDb;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Transactional
public class MenuPesananServiceImpl implements MenuPesananService{
    @Autowired
    MenuPesananDb menuPesananDb;

    @Override
    public MenuPesanan findMenuPesananId(Long id){
        Optional<MenuPesanan> menuPesanan = menuPesananDb.findById(id);
        if(menuPesanan.isPresent()){
            return menuPesanan.get();
        }else return null;
    }


}
