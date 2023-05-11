package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.model.Role;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("cabangServiceImpl")
    private CabangService cabangService;

    @Autowired
    @Qualifier("adminServiceImpl")
    private AdminService adminService;

    @Autowired
    @Qualifier("manajerServiceImpl")
    private ManajerService manajerService;

    @Autowired
    @Qualifier("baristaServiceImpl")
    private BaristaService baristaService;

    @Autowired
    @Qualifier("staffInventoryServiceImpl")
    private StaffInventoryService staffInventoryService;

    @Autowired
    @Qualifier("staffPabrikServiceImpl")
    private StaffPabrikService staffPabrikService;


    @GetMapping("/dummy")
    public String addAdminDummy (Model model){
        Admin admin = new Admin();
        admin.setNama("admin");
        admin.setUsername("admin");
        Cabang cb = cabangService.findCabangId(1L);
        admin.setCabang(cb);
        admin.setRole(Role.ADMIN);
        admin.setPassword("admin");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminService.addAdmin(admin);
        return "redirect:/";
    }

    @RequestMapping("/user")
    public String viewAllUser(Model model, Authentication authentication) {
        // get role
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        Role role = user.getRole();
        String cabang = user.getCabang().getNama();

        if (role.equals(Role.ADMIN)){
            List<UserModel> listUser = userService.getListUser(role, cabang);
            model.addAttribute("listUser", listUser);
            return "user/view-all-user";
        }

        else{
            List<UserModel> listUser = userService.getListUser(role, cabang);
            model.addAttribute("listUser", listUser);
            return "user/view-all-user";
        }
    }

    //update menu
    @GetMapping("/user/update/{id}")
    public String updatePasswordForm (@PathVariable Long id, Model model, Authentication authentication){
        UserModel user = userService.findUserId(id);

        model.addAttribute("user", user);
        System.out.println("getmap");

        return "user/update";
    }

    @PostMapping(value="user/update")
    public String updatePasswordSubmit(@ModelAttribute UserModel user, Model model, Authentication authentication){
        Long id = user.getId();

        if (user.getRole().equals(Role.MANAJER)){
            Manajer manajer = manajerService.findManajerId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            manajer.setPassword(encoder.encode(user.getPassword()));
            manajerService.update(manajer);
        }

        if (user.getRole().equals(Role.BARISTA)){
            Barista barista = baristaService.findBaristaId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            barista.setPassword(encoder.encode(user.getPassword()));
            baristaService.update(barista);
        }

        if (user.getRole().equals(Role.StaffInventory)){
            StaffInventory staff= staffInventoryService.findStaffInventoryId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            staff.setPassword(encoder.encode(user.getPassword()));
            staffInventoryService.update(staff);
        }

        if (user.getRole().equals(Role.StaffPabrik)){
            StaffPabrik staff= staffPabrikService.findStaffPabrikId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            staff.setPassword(encoder.encode(user.getPassword()));
            staffPabrikService.update(staff);
        }

        return "redirect:/user";
    }

    //update menu
    @GetMapping("/user/update/{id}")
    public String updatePasswordForm (@PathVariable Long id, Model model, Authentication authentication){
        UserModel user = userService.findUserId(id);

        model.addAttribute("user", user);
        System.out.println("getmap");

        return "user/update";
    }

    @PostMapping(value="user/update")
    public String updatePasswordSubmit(@ModelAttribute UserModel user, Model model, Authentication authentication){
        Long id = user.getId();

        if (user.getRole().equals(Role.MANAJER)){
            Manajer manajer = manajerService.findManajerId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            manajer.setPassword(encoder.encode(user.getPassword()));
            manajerService.update(manajer);
        }

        if (user.getRole().equals(Role.BARISTA)){
            Barista barista = baristaService.findBaristaId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            barista.setPassword(encoder.encode(user.getPassword()));
            baristaService.update(barista);
        }

        if (user.getRole().equals(Role.StaffInventory)){
            StaffInventory staff= staffInventoryService.findStaffInventoryId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            staff.setPassword(encoder.encode(user.getPassword()));
            staffInventoryService.update(staff);
        }

        if (user.getRole().equals(Role.StaffPabrik)){
            StaffPabrik staff= staffPabrikService.findStaffPabrikId(id);
            //encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            staff.setPassword(encoder.encode(user.getPassword()));
            staffPabrikService.update(staff);
        }

        return "redirect:/user";
    }

    @GetMapping("/user/addmanajer")
    public String addManajerForm (Model model, Authentication authentication){
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();


        Manajer manajer = new Manajer();
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("manajer", manajer);
        model.addAttribute("listCabang", listCabang);
        model.addAttribute("cabang", cabang);
        return "user/form-add-manajer";
    }

    @PostMapping(value = "/user/addmanajer")
    public String addManajerSubmit(@ModelAttribute Manajer manajer, Model model, RedirectAttributes redirectAttrs, Authentication authentication) {
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        //encrypt password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        manajer.setPassword(encoder.encode(manajer.getPassword()));

        manajer.setCabang(user.getCabang());
        manajer.setRole(Role.MANAJER);
        manajerService.addManajer(manajer);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", manajer.getRole(), manajer.getNama()));
        return "redirect:/user";
    }

    @GetMapping("/user/addbarista")
    public String addBaristaForm (Model model, Authentication authentication){
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();

        Barista barista = new Barista();
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("barista", barista);
        model.addAttribute("listCabang", listCabang);
        model.addAttribute("cabang", cabang);
        return "user/form-add-barista";
    }

    @PostMapping(value = "/user/addbarista")
    public String addBaristaSubmit(@ModelAttribute Barista barista, Model model, RedirectAttributes redirectAttrs, Authentication authentication) {
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        //encrypt password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        barista.setPassword(encoder.encode(barista.getPassword()));

        barista.setCabang(user.getCabang());
        barista.setRole(Role.BARISTA);
        baristaService.addBarista(barista);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", barista.getRole(), barista.getNama()));
        return "redirect:/user";
    }

    @GetMapping("/user/addstafinv")
    public String addInventoryStaffForm (Model model, Authentication authentication){
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();

        StaffInventory staff = new StaffInventory();
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("staff", staff);
        model.addAttribute("listCabang", listCabang);
        model.addAttribute("cabang", cabang);
        return "user/form-add-staff-inventory";
    }

    @PostMapping(value = "/user/addstafinv")
    public String addInventoryStaffSubmit(@ModelAttribute StaffInventory staff, Model model, RedirectAttributes redirectAttrs, Authentication authentication) {
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        // encrypt password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        staff.setPassword(encoder.encode(staff.getPassword()));

        staff.setCabang(user.getCabang());
        staff.setRole(Role.StaffInventory);
        staffInventoryService.addStaff(staff);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", staff.getRole(), staff.getNama()));
        return "redirect:/user";
    }

    @GetMapping("/user/addstafpabrik")
    public String addFactoryStaffForm (Model model, Authentication authentication){
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();

        StaffPabrik staff = new StaffPabrik();
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("staff", staff);
        model.addAttribute("listCabang", listCabang);
        model.addAttribute("cabang", cabang);
        return "user/form-add-staff-pabrik";
    }

    @PostMapping(value = "/user/addstafpabrik")
    public String addFactoryStaffSubmit(@ModelAttribute StaffPabrik staff, Model model, RedirectAttributes redirectAttrs, Authentication authentication) {
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        // encrypt password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        staff.setPassword(encoder.encode(staff.getPassword()));

        staff.setRole(Role.StaffPabrik);
        staffPabrikService.addStaff(staff);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", staff.getRole(), staff.getNama()));
        return "redirect:/user";
    }


}