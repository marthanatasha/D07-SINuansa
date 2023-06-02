package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.service.InventoryService;
import propensi.sinuansa.SINuansa.service.MenuService;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {
    @Qualifier("inventoryServiceImpl")
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("menuServiceImpl")
    private MenuService menuService;

//    @GetMapping("/inventory/add")
//    public String addInventoryFormPage(Model model){
//        Inventory inventory = new Inventory();
//        model.addAttribute("inventory", inventory);
//        return "inventory/form-add-inventory";
//    }
//    @PostMapping("/inventory/add")
//    public String addInventorySubmitPage(@ModelAttribute Inventory inventory, Model model) {
//        inventoryService.addInventory(inventory);
//        model.addAttribute("id", inventory.getId());
//        return "inventory/add-inventory";
//    }

    @RequestMapping ("/inventory/add")
    public String addInventoryModal(Model model,
                                    @RequestParam(value="is_kopi",required = false) Boolean is_kopi,
                                    @RequestParam(value="quantity1",required = false) Integer quantity1,

                                    @RequestParam(value="name",required = false) String name,
                                    @RequestParam(value="temp",required = false) Long temp, Principal principal){
        Inventory inventory = new Inventory();
        inventory.setKopi(is_kopi);
        inventory.setJumlah(quantity1);
        inventory.setKategori("none");
        inventory.setNama(name);
        UserModel user = userService.findByUsername(principal.getName());
        inventory.setCabang(user.getCabang());
        inventoryService.addInventory(inventory);
        model.addAttribute("inventory", inventory);
        return "redirect:/inventory/viewall";
    }

    @RequestMapping ("/inventory/update")
    public String updateInventoryModal(Model model,
                                        @RequestParam(value="quantity",required = false) Integer quantity,
                                        @RequestParam(value="temp",required = false) Long temp, Authentication authentication){
        UserModel user = userService.findByUsername(authentication.getName());
        Inventory inventory = inventoryService.getInventoryById(temp);
        inventory.setJumlah(quantity);
        Inventory updatedInventory = inventoryService.updateInventory(inventory);
        model.addAttribute("id", updatedInventory.getId());
        for(Menu menu : menuService.getAllMenu(user.getCabang().getNama())){
            Boolean cekAvailable = menuService.availabilityCheck(menu);
            if(!cekAvailable){
                menu.setStatus(false);
                menuService.updateMenu(menu);
            }else{
                menu.setStatus(true);
                menuService.updateMenu(menu);
            }
        }
        return "redirect:/inventory/viewall";
    }

//    @GetMapping("/inventory/delete/{id}")
//    public String deleteInventorySubmitPage(@PathVariable Long id, Model model){
//        Inventory inventory = inventoryService.getInventoryById(id);
//        Long idThis = inventory.getId();
//        Inventory deletedInventory = inventoryService.deleteInventory(inventory);
//        model.addAttribute("id", idThis);
//        return "redirect:/inventory/viewall";
//    }

    @GetMapping("/inventory/viewall")
    public String listInventory(Model model, Authentication authentication){
        List<Inventory> listInventory = inventoryService.getListInventory();
        List<Inventory> newListInventory = new ArrayList<>();
        UserModel user = userService.findByUsername(authentication.getName());

        for(Inventory i : listInventory) {
            if(i.getCabang().getId()==user.getCabang().getId()){
                newListInventory.add(i);
            }
        }
        model.addAttribute("listInventory", newListInventory);
        return "inventory/viewall-inventory";
    }

}

