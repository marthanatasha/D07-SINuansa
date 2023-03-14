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
import propensi.sinuansa.SINuansa.service.InventoryService;

import java.util.List;

@Controller
public class InventoryController {
    @Qualifier("inventoryServiceImpl")
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/inventory/add")
    public String addInventoryFormPage(Model model){
        Inventory inventory = new Inventory();
        model.addAttribute("inventory", inventory);
        return "inventory/form-add-inventory";
    }
    @PostMapping("/inventory/add")
    public String addInventorySubmitPage(@ModelAttribute Inventory inventory, Model model) {
        inventoryService.addInventory(inventory);
        model.addAttribute("id", inventory.getId());
        return "inventory/add-inventory";
    }

    @GetMapping("/inventory/update/{id}")
    public String updateInventoryFormPage(@PathVariable Long id, Model model){
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory", inventory);
        return "inventory/form-update-inventory";
    }

    @PostMapping("/inventory/update")
    public String updateInventorySubmitPage(@ModelAttribute Inventory inventory, Model model){
        Inventory updatedInventory = inventoryService.updateInventory(inventory);
        model.addAttribute("id", updatedInventory.getId());
        return "inventory/update-inventory";
    }

    @GetMapping("/inventory/delete/{id}")
    public String deleteInventorySubmitPage(@PathVariable Long id, Model model){
        Inventory inventory = inventoryService.getInventoryById(id);
        Inventory deletedInventory = inventoryService.deleteInventory(inventory);
        model.addAttribute("id", inventory.getId());
        return "inventory/delete-inventory";

    }

    @GetMapping("/inventory/viewall")
    public String listInventory(Model model){
        List<Inventory> listInventory = inventoryService.getListInventory();
        model.addAttribute("listInventory", listInventory);
        return "inventory/viewall-inventory";
    }


}

