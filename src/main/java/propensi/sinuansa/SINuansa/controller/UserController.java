package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.model.Role;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.service.*;

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

    @RequestMapping("/user")
    public String viewAllUser(Model model) {
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);
        return "user/viewall-user";
    }

    @GetMapping("/user/addmanajer")
    public String addManajerForm (Model model){
        Manajer manajer = new Manajer();
//        manajer.setRole(Role.MANAJER);
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("manajer", manajer);
        model.addAttribute("listCabang", listCabang);
        return "user/form-add-manajer";
    }

    @PostMapping(value = "/user/addmanajer")
    public String addManajerSubmit(@ModelAttribute Manajer manajer, Model model, RedirectAttributes redirectAttrs) {
        // to do: encrypt password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        manajer.setPassword(encoder.encode(manajer.getPassword()));
        manajer.setRole(Role.MANAJER);
        manajerService.addManajer(manajer);
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", manajer.getRole(), manajer.getNama()));
        return "redirect:/user";
    }

    @GetMapping("/user/addbarista")
    public String addBaristaForm (Model model){
        Barista barista = new Barista();
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("barista", barista);
        model.addAttribute("listCabang", listCabang);
        return "user/form-add-barista";
    }

    @PostMapping(value = "/user/addbarista")
    public String addBaristaSubmit(@ModelAttribute Barista barista, Model model, RedirectAttributes redirectAttrs) {
        // to do: encrypt password
        barista.setRole(Role.BARISTA);
        baristaService.addBarista(barista);
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", barista.getRole(), barista.getNama()));
        return "redirect:/user";
    }

    @GetMapping("/user/addstafinv")
    public String addInventoryStaffForm (Model model){
        StaffInventory staff = new StaffInventory();
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("staff", staff);
        model.addAttribute("listCabang", listCabang);
        return "user/form-add-staff-inventory";
    }

    @PostMapping(value = "/user/addstafinv")
    public String addInventoryStaffSubmit(@ModelAttribute StaffInventory staff, Model model, RedirectAttributes redirectAttrs) {
        // to do: encrypt password
        staff.setRole(Role.StaffInventory);
        staffInventoryService.addStaff(staff);
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", staff.getRole(), staff.getNama()));
        return "redirect:/user";
    }

    @GetMapping("/user/addstafpabrik")
    public String addFactoryStaffForm (Model model){
        StaffPabrik staff = new StaffPabrik();
        List<Cabang> listCabang = cabangService.getListCabang();
        model.addAttribute("staff", staff);
        model.addAttribute("listCabang", listCabang);
        return "user/form-add-staff-pabrik";
    }

    @PostMapping(value = "/user/addstafpabrik")
    public String addFactoryStaffSubmit(@ModelAttribute StaffPabrik staff, Model model, RedirectAttributes redirectAttrs) {
        // to do: encrypt password
        staff.setRole(Role.StaffPabrik);
        staffPabrikService.addStaff(staff);
        List<UserModel> listUser = userService.getListUser();
        model.addAttribute("listUser", listUser);

        redirectAttrs.addFlashAttribute("success",
                String.format("%s dengan nama %s berhasil disimpan!", staff.getRole(), staff.getNama()));
        return "redirect:/user";
    }


}
