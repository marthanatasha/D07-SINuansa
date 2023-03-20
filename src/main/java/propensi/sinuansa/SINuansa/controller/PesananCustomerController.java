package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Menu;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.service.PesananCustomerService;

import java.util.List;

public class PesananCustomerController {

//    @Autowired
//    private MenuService menuService;

    @Qualifier("pesananCustomerServiceImpl")
    @Autowired
    private PesananCustomerService pesananCustomerService;

    @GetMapping("/pesananCustomer/add")
    public String addPesananCustomerFormPage(Model model){
        PesananCustomer pesananCustomer = new PesananCustomer();
        model.addAttribute("pesananCustomer", pesananCustomer);
        return "pesananCustomer/form-add-pesananCustomer";
    }
    @PostMapping("/pesananCustomer/add")
    public String addPesananCustomerPage(@ModelAttribute PesananCustomer pesananCustomer, Model model) {
        pesananCustomerService.addPesananCustomer(pesananCustomer);
        model.addAttribute("id", pesananCustomer.getId());
        return "pesananCustomer/add-pesananCustomer";
    }

    @GetMapping("/inventory/viewall")
    public String listInventory(Model model){
//        String[][] menu = {"cabang A": {"jus jeruk": "10000"}, "cabang B": {}}
//        page pesen menu
//        List<Menu> allMenu = menuService.getAllMenuByCabang(....);
//        model.addAtrribute("allMenu", allMenu);
        return "inventory/viewall-inventory";
    }
}
