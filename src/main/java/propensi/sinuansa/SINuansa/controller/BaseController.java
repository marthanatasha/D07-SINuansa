package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.service.AdminService;
import propensi.sinuansa.SINuansa.service.CabangService;
import propensi.sinuansa.SINuansa.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BaseController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("cabangServiceImpl")
    private CabangService cabangService;

    @Autowired
    @Qualifier("adminServiceImpl")
    private AdminService adminService;

    @GetMapping("/")
    public String checkAdmin(Model model, Authentication authentication) {
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        if (user.getRole().equals(Role.ADMIN)){
            List<Cabang> listCabang = cabangService.getListCabang();
            model.addAttribute("listCabang", listCabang);
            model.addAttribute("user", user);
            return "admin-page";
        }
        else{
            return "redirect:/home";
        }
//        return "home";
    }

    @PostMapping("/")
    public String checkAdmin(@ModelAttribute UserModel user, Model model){
        Long id = user.getId();
        Admin admin = adminService.findAdminId(id);
        admin.setCabang(user.getCabang());
        adminService.updateCabang(admin);
        return "redirect:/home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/info")
    public String info(){ return "error/error-pass";}

}
