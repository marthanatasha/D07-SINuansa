package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Menu;
import propensi.sinuansa.SINuansa.model.MenuPesanan;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.service.MenuPesananService;
import propensi.sinuansa.SINuansa.service.MenuService;
import propensi.sinuansa.SINuansa.service.PesananCustomerService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
@Controller
public class PesananCustomerController {

    @Qualifier("pesananCustomerServiceImpl")
    @Autowired
    private PesananCustomerService pesananCustomerService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuPesananService menuPesananService;

    @GetMapping("/pesananCustomer/add")
    public String addPesananCustomerForm(Model model){
        PesananCustomer pesananCustomer = new PesananCustomer();
        List<Menu> listMenu = menuService.getListMenu();
        List<MenuPesanan> listMenuPesanan = menuPesananService.getListMenuPesanan();
        List<MenuPesanan> listMenuPesananNew = new ArrayList<>();

        pesananCustomer.setMenuPesananList(listMenuPesananNew);
        pesananCustomer.getMenuPesananList().add(new MenuPesanan());

        model.addAttribute("pesananCustomer", pesananCustomer);
        model.addAttribute("listMenu", listMenu);
        model.addAttribute("listMenuPesanan", listMenuPesanan);
        return "pesananCustomer/tes";
    }
    @PostMapping(value ="/pesananCustomer/add", params = {"save"})
    public String addPesananCustomerSubmit(@ModelAttribute PesananCustomer pesananCustomer, Model model, RedirectAttributes redirectAttrs) {
        ArrayList<String> namaMenu = new ArrayList<String>( );
        ArrayList<Long> jumlahMenu = new ArrayList<Long>( );
        ArrayList<Long> hargaMenu = new ArrayList<Long>( );
        ArrayList<Long> totalHargaMenu = new ArrayList<Long>( );
        Long total_harga = 0L;
        if(pesananCustomer.getMenuPesananList() == null){
            pesananCustomer.setMenuPesananList(new ArrayList<>());
        } else{
            int idx =0;
            pesananCustomer.setDiskon(0L);
            pesananCustomer.setHarga(0L);
            pesananCustomerService.addPesananCustomer(pesananCustomer);
            for(MenuPesanan menuPesanan : pesananCustomer.getMenuPesananList()){
                menuPesanan.setPesananCustomer(pesananCustomer);
                Menu cariMenu = menuService.findMenuId(pesananCustomer.getMenuPesananList().get(idx).getMenu().getId());
                namaMenu.add(cariMenu.getNama());
                hargaMenu.add(cariMenu.getHarga());
                menuPesanan.setMenu(cariMenu);
                menuPesanan.setJumlah(pesananCustomer.getMenuPesananList().get(idx).getJumlah());
                jumlahMenu.add(pesananCustomer.getMenuPesananList().get(idx).getJumlah());
                total_harga = total_harga + cariMenu.getHarga()*menuPesanan.getJumlah();
                totalHargaMenu.add(cariMenu.getHarga()*menuPesanan.getJumlah());
                menuPesananService.addMenuPesanan(menuPesanan);
                idx++;
            }
            pesananCustomer.setHarga(total_harga);
        }
        pesananCustomerService.addPesananCustomer(pesananCustomer);
        Long id = pesananCustomer.getId();
        model.addAttribute("id", id);
        model.addAttribute("namaMenu", namaMenu);
        model.addAttribute("jumlahMenu", jumlahMenu);
        model.addAttribute("hargaMenu", hargaMenu);
        model.addAttribute("totalHargaMenu", totalHargaMenu);
        model.addAttribute("total_harga", total_harga);

        return "pesananCustomer/summary-pesananCustomer";
    }

    //add Row
    @PostMapping(value ="/pesananCustomer/add", params = {"addRow"})
    public String addRowPesananCustomer(@ModelAttribute PesananCustomer pesananCustomer, Model model) {
        List<Menu> listMenu = menuService.getListMenu();
        if(pesananCustomer.getMenuPesananList() == null || pesananCustomer.getMenuPesananList().size()==0){
            pesananCustomer.setMenuPesananList(new ArrayList<>());
        }
        pesananCustomer.getMenuPesananList().add(new MenuPesanan());
        List<MenuPesanan> listMenuPesanan = menuPesananService.getListMenuPesanan();

        model.addAttribute("pesananCustomer", pesananCustomer);
        model.addAttribute("listMenu", listMenu);
        model.addAttribute("listMenuPesanan", listMenuPesanan);
        return "pesananCustomer/tes";
    }

    //delete Row
    @PostMapping(value ="/pesananCustomer/add", params = {"deleteRow"})
    public String deleteRowPesananCustomer(@ModelAttribute PesananCustomer pesananCustomer, @RequestParam("deleteRow") Integer row, Model model) {
        List<Menu> listMenu = menuService.getListMenu();
        final Integer rowInt = Integer.valueOf(row);
        pesananCustomer.getMenuPesananList().remove(rowInt.intValue());

        List<MenuPesanan> listMenuPesanan = pesananCustomer.getMenuPesananList();

        model.addAttribute("pesananCustomer", pesananCustomer);
        model.addAttribute("listMenu", listMenu);
        model.addAttribute("listMenuPesanan", listMenuPesanan);
        return "pesananCustomer/tes";
    }

}

