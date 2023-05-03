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

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping("/menu")
    public String viewAllMenu(Model model, Authentication authentication) {
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();
        Role role = user.getRole();

        //manajer cm bisa liat menu di cabang msing2
        if (role.equals(Role.MANAJER)){
            List<Menu> listMenu = menuService.getListMenuByCabangToHide(cabang);

            // notes: buat kalo langsung dri link tetep gabisa dibuka (meskipun tombol udh di disabled)
            //Boolean editable = menuService.canEdit(LocalTime.now());
            Boolean editable = menuService.canEdit(LocalTime.of(23,00,00));

            //Boolean deleteable = menuService.canDelete(LocalTime.now());
            Boolean deleteable = menuService.canDelete(LocalTime.of(23,00,00));

            model.addAttribute("listMenu", listMenu);
            model.addAttribute("editable", editable);
            model.addAttribute("deletable", deleteable);
            return "menu/view-all-menu";

        }

        //admin karna diawal udh pilih cabang mana yg mau diatur, jd sama jg cm bisa liat menu di cbg yg dipilih
        // tapi sementara karna admin blm bisa milih cabang di awal, admin bisa liat semua menu dlu
        else{
            List<Menu> listMenu = menuService.getListMenu();

            // notes: buat kalo langsung dri link tetep gabisa dibuka (meskipun tombol udh di disabled)
            Boolean editable = menuService.canEdit(LocalTime.now());
            Boolean deleteable = menuService.canDelete(LocalTime.now());

            model.addAttribute("listMenu", listMenu);
            model.addAttribute("editable", editable);
            model.addAttribute("deletable", deleteable);
            return "menu/view-all-menu";
        }
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
    public String addMenuSubmit (@ModelAttribute Menu menu, Model model, RedirectAttributes redirectAttrs, Authentication authentication){
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

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
        menu.setCabang(user.getCabang());

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
    public String updateMenuForm (@PathVariable Long id, Model model, Authentication authentication){
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        Menu menu = menuService.findMenuId(id);
        List<Inventory> listInventory = inventoryService.getListInventory();
        List<Resep> listResep = menu.getResepList();

        model.addAttribute("menu", menu);
        model.addAttribute("listInventory", listInventory);
        model.addAttribute("listResep", listResep);
        model.addAttribute("status", menu.getStatus());
        model.addAttribute("isShow", menu.getIsShow());
        model.addAttribute("cabang", menu.getCabang());

        return "menu/form-update";
    }

    @PostMapping(value="menu/update", params = {"save"})
    public String updateMenuSubmit(@ModelAttribute Menu menu, Model model, Authentication authentication){
        Long id = menu.getId();

        // cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        Cabang cabang = user.getCabang();

        // status, isShow
        Boolean status = menu.getStatus();
        Boolean isShow = menu.getIsShow();

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
        menu.setCabang(cabang);
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
    public String deleteMenuForm (Model model, Authentication authentication){
        // versi seharusnya
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();
        Role role = user.getRole();

        List<Menu> listMenu = new ArrayList<>();
        //manajer cm bisa liat apus(hide) di cabang msing2
        if (role.equals(Role.MANAJER)){
            listMenu = menuService.getListMenuByCabangToHide(cabang);

        }
        //admin karna diawal udh pilih cabang mana yg mau diatur, jd sama jg cm bisa hide menu di cbg yg dipilih
        // tapi sementara karna admin blm bisa milih cabang di awal, admin bisa hide semua menu dlu
        else{
            listMenu = menuService.getListMenu();
        }

        model.addAttribute("listMenu", listMenu);

        return "menu/form-hide";


        /** sebelum revisi
        List<Menu> listMenu = menuService.getListMenu();
        model.addAttribute("listMenu", listMenu);
        return "menu/form-hide";
        **/
    }

    @RequestMapping(value = "menu/hide/{ids}", method = RequestMethod.GET)
    public String hideBatchHandler(@PathVariable Long[] ids, Model model) {


        menuService.hideMenu(ids);
//        return deleteMenuForm(model);
        return "redirect:/menu";
    }

    //show (add) menu
    @GetMapping("/menu/show")
    public String showMenuForm (Model model, Authentication authentication){
        // versi seharusnya
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();
        Role role = user.getRole();

        List<Menu> listMenu = new ArrayList<>();
        //manajer cm bisa liat apus(hide) di cabang msing2
        if (role.equals(Role.MANAJER)){
            listMenu = menuService.getListMenuByCabangToShow(cabang);

        }
        //admin karna diawal udh pilih cabang mana yg mau diatur, jd sama jg cm bisa hide menu di cbg yg dipilih
        // tapi sementara karna admin blm bisa milih cabang di awal, admin bisa hide semua menu dlu
        else{
            listMenu = menuService.getListMenu();
        }

        model.addAttribute("listMenu", listMenu);

        return "menu/form-show";


        /** sebelum revisi
         List<Menu> listMenu = menuService.getListMenu();
         model.addAttribute("listMenu", listMenu);
         return "menu/form-hide";
         **/
    }

    @RequestMapping(value = "menu/show/{ids}", method = RequestMethod.GET)
    public String showBatchHandler(@PathVariable Long[] ids, Model model) {


        menuService.showMenu(ids);
//        return deleteMenuForm(model);
        return "redirect:/menu";
    }

}

