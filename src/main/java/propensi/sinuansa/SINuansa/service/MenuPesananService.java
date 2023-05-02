package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.MenuPesanan;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.model.Resep;

import java.util.List;

import java.time.LocalTime;

public interface MenuPesananService {
    MenuPesanan findMenuPesananId(Long Id);
    List<MenuPesanan> getListMenuPesanan();
    void addMenuPesanan(MenuPesanan menuPesanan);
}
