package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.sinuansa.SINuansa.service.PembayaranService;

@Controller
@RequestMapping("/payment")
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @GetMapping("/success/{method}/{source}")
    public String postPayment(@PathVariable String method, @PathVariable String source,Model model){
        System.out.println(method);
        System.out.println(source);
        return "redirect:/";
    }
}
