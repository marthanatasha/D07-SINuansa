package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {
    @Autowired
    @Qualifier("menuServiceImpl")
    private MenuService menuService;

    @Autowired
    @Qualifier("inventoryServiceImpl")
    private InventoryService inventoryService;

    @Autowired
    @Qualifier("resepServiceImpl")
    private ResepService resepService;

    @RequestMapping("/menu")
    public String viewAllMenu(Model model) {
        List<Menu> listMenu = menuService.getListMenu();
        model.addAttribute("listMenu", listMenu);
        return "menu/viewall-menu";
    }

    @GetMapping("/menu/add")
    public String addMenuForm(Model model){
        Menu menu = new Menu();
        List<Inventory> listInventory = inventoryService.getListInventory();
        List<Resep> listResep = resepService.getListResep();
        List<Resep> listResepNew = new ArrayList<>();

        menu.setResepList(listResepNew);
        menu.getResepList().add(new Resep());

        model.addAttribute("menu", menu);
        model.addAttribute("listInventory", listInventory);
        model.addAttribute("listResepExisting", listResep);

        return "menu/form-add-menu";
    }

    @PostMapping(value = "/menu/add", params = {"save"})
    public String addMenuSubmit (@ModelAttribute Menu menu, Model model){
            if (menu.getResepList() == null) {
                menu.setResepList(new ArrayList<>());
            } else {
                int idx = 0;
                for (Resep resep : menu.getResepList()) {
                    resep.setMenu(menu);
                    resep.setInventory(menu.getResepList().get(idx).getInventory());
                    idx++;
                }
            }

            //to do: atur supaya cabang disimpen berdasarkan siapa yg lagi login
            // sementara cabang jadi field dulu ketika add menu
            menuService.addMenu(menu);
            model.addAttribute("menu", menu);
            return "menu/viewall-menu";
    }


    //tambah resep
    @PostMapping(value = "/menu/add", params = {"addRow"})
    private String addRowResep(@ModelAttribute Menu menu, Model model){
        List<Inventory> listInventory = inventoryService.getListInventory();
        if (menu.getResepList() == null || menu.getResepList().size() == 0){
            menu.setResepList(new ArrayList<>());
        }
        menu.getResepList().add(new Resep());
        List<Resep> listResep = resepService.getListResep();

        model.addAttribute("menu", menu);
        model.addAttribute("listResepExisting", listResep);
        model.addAttribute("listInventory", listInventory);

        return "menu/form-add-menu";
    }

    // delete row resep
    @PostMapping(value = "/menu/add", params = {"deleteRowResep"})
    private String deleteRowResep (@ModelAttribute Menu menu, @RequestParam("deleteRowResep") Integer row, Model model){
        List<Inventory> listInventory = inventoryService.getListInventory();
        final Integer rowInt = Integer.valueOf(row);
        menu.getResepList().remove(rowInt.intValue());

        List<Resep> listResep = menu.getResepList();

        model.addAttribute("menu", menu);
        model.addAttribute("listResepExisting", listResep);
        model.addAttribute("listInventory", listInventory);

        return "menu/form-add-menu";
    }

    //delete menu

}

