package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.service.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
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

    @Autowired
    private UserService userService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/pesananCustomer/add")
    public String addPesananCustomerForm(Model model, Authentication authentication){
        UserModel user = userService.findByUsername(authentication.getName());
        PesananCustomer pesananCustomer = new PesananCustomer();
        List<Menu> listMenu = menuService.getListMenu(user.getCabang().getNama());
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
    public String addPesananCustomerSubmit(@ModelAttribute PesananCustomer pesananCustomer, Model model, RedirectAttributes redirectAttrs, Authentication authentication) {
        UserModel user = userService.findByUsername(authentication.getName());
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
            pesananCustomer.setWaktu(LocalDateTime.now());
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

        for(MenuPesanan menuPesanan : pesananCustomer.getMenuPesananList()){
            Menu menuNow = menuPesanan.getMenu();
            for(Resep resep : menuNow.getResepList()){
                Inventory inventoryNow = resep.getInventory();
                Long temp = menuPesanan.getJumlah()*resep.getJumlah();
                Long longInvent = (long)(int)inventoryNow.getJumlah()-temp;
                inventoryNow.setJumlah((int)(long)longInvent);
                inventoryService.addInventory(inventoryNow);
            }
        }

        for(Menu menu : menuService.getAllMenu(user.getCabang().getNama())){
            Boolean cekAvailable = menuService.availabilityCheck(menu);
            if(!cekAvailable){
                menu.setStatus(false);
                menuService.updateMenu(menu);
            }
        }
        return "pesananCustomer/summary-pesananCustomer";
    }

    //add Row
    @PostMapping(value ="/pesananCustomer/add", params = {"addRow"})
    public String addRowPesananCustomer(@ModelAttribute PesananCustomer pesananCustomer, Model model, Authentication authentication) {
        UserModel user = userService.findByUsername(authentication.getName());
        List<Menu> listMenu = menuService.getListMenu(user.getCabang().getNama());
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
    public String deleteRowPesananCustomer(@ModelAttribute PesananCustomer pesananCustomer, @RequestParam("deleteRow") Integer row, Model model, Authentication authentication) {
        UserModel user = userService.findByUsername(authentication.getName());
        List<Menu> listMenu = menuService.getListMenu(user.getCabang().getNama());
        final Integer rowInt = Integer.valueOf(row);
        pesananCustomer.getMenuPesananList().remove(rowInt.intValue());

        List<MenuPesanan> listMenuPesanan = pesananCustomer.getMenuPesananList();

        model.addAttribute("pesananCustomer", pesananCustomer);
        model.addAttribute("listMenu", listMenu);
        model.addAttribute("listMenuPesanan", listMenuPesanan);

        return "pesananCustomer/tes";

    }

}

