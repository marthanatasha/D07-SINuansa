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
        return "menu/view-all-menu";
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
    public String addMenuSubmit (@ModelAttribute Menu menu, Model model, RedirectAttributes redirectAttrs){
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

        //to do: cek status availability
        // menu.setStatus(menuService.availabilityCheck(menu));
        menu.setStatus(true);
        menu.setIsShow(true);
        menuService.addMenu(menu);
        model.addAttribute("menu", menu);
        return "redirect:/menu";
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

    //update menu
    @GetMapping("/menu/update/{id}")
    public String updateMenuForm (@PathVariable Long id, Model model){
        Menu menu = menuService.findMenuId(id);
        List<Inventory> listInventory = inventoryService.getListInventory();
        List<Resep> listResep = menu.getResepList();

        model.addAttribute("menu", menu);
        model.addAttribute("listInventory", listInventory);
        model.addAttribute("listResep", listResep);

        return "menu/form-update";
    }

    @PostMapping(value="menu/update", params = {"save"})
    public String updateMenuSubmit(@ModelAttribute Menu menu, Model model){
        Long id = menu.getId();
        List<Resep> oldList = menuService.findMenuId(id).getResepList();
        List<Resep> allResep = resepService.getListResep();
        if (menu.getResepList() == null){
            menu.setResepList(new ArrayList<>());
        }
        else{
            int idx = 0;
            for (Resep resep : menu.getResepList()){
                resep.setMenu(menu);
                resep.setInventory(menu.getResepList().get(idx).getInventory());
                idx++;
            }
        }

        menu.setStatus(true);
        menu.setIsShow(true);
        menuService.updateMenu(menu);
        model.addAttribute("id", menu.getId());
        return "redirect:/menu";
    }

    //addRow untuk update
    //tambah reseo
    @PostMapping(value="/menu/update", params = {"addRowUpdate"})
    private String addRowUpdate(@ModelAttribute Menu menu, Model model){
        List<Inventory> listInventory = inventoryService.getListInventory();
        if (menu.getResepList() == null || menu.getResepList().size() == 0){
            menu.setResepList(new ArrayList<>());
        }
        List<Resep> listResep = menu.getResepList();
        menu.getResepList().add(new Resep());

        model.addAttribute("menu", menu);
        model.addAttribute("listResep", listResep);
        model.addAttribute("listInventory", listInventory);

        return "menu/form-update";
    }

    //deleteRow untuk update
    @PostMapping(value="/menu/update", params = {"deleteRowUpdate"})
    private String deleteRowUpdate (@ModelAttribute Menu menu, @RequestParam("deleteRowUpdate") Integer row, Model model){
        List<Inventory> listInventry = inventoryService.getListInventory();
        final Integer rowInt = Integer.valueOf(row);
        Resep resep = menu.getResepList().get(rowInt.intValue());
        menu.getResepList().remove(rowInt.intValue());
        resepService.deleteResep(resep);

        List<Resep> listResep = menu.getResepList();

        model.addAttribute("menu", menu);
        model.addAttribute("listResepExisting", listResep);
        model.addAttribute("listInventory", listInventry);

        return "menu/form-update";
    }

    //hide (delete) menu
    @GetMapping("/menu/hide")
    public String deleteMenuForm (Model model){
        List<Menu> listMenu = menuService.getListMenu();


        model.addAttribute("listMenu", listMenu);

        return "menu/form-hide";
    }

    @RequestMapping(value = "menu/hide/{ids}", method = RequestMethod.GET)
    public String hideBatchHandler(@PathVariable Long[] ids, Model model) {


        menuService.hideMenu(ids);
//        return deleteMenuForm(model);
        return "redirect:/menu";
    }

//    @GetMapping(value = "${hidebatch}/{ids}")
//    public String hideBatch(@PathVariable Long[] ids, Model model) {
//        menuService.hideMenu(ids);
//        return deleteMenuForm(model);
////        return "menu/view-all-menu";
//    }

}

