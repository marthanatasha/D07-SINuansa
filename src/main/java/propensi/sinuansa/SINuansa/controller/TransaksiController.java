package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.sinuansa.SINuansa.DTO.TransaksiDTO;
import propensi.sinuansa.SINuansa.model.Transaksi;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.service.TransaksiService;
import propensi.sinuansa.SINuansa.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransaksiController {

    @Autowired
    TransaksiService transaksiService;

    @Autowired
    UserService userService;

    @GetMapping("/history")
    public String lihatTransaksi(Model model, Authentication authentication){
        UserModel user = userService.findByUsername(authentication.getName());
        Long idCabang = user.getCabang().getId();
        List<Transaksi> listTransaksi = transaksiService.getTransaksiList();
        List<TransaksiDTO> listTransaksiDTO = new ArrayList<TransaksiDTO>();
        for(Transaksi transaksi : listTransaksi){
            if(idCabang == transaksi.getCabang().getId()) {
                TransaksiDTO transaksiDTO = new TransaksiDTO(transaksi.getId(), transaksi.getNama(), transaksi.getAkun(), transaksi.getRefCode(), transaksi.getKategori(),
                        transaksi.getNominal(), transaksi.getKuantitas(), transaksi.getWaktuTransaksi());
                listTransaksiDTO.add(transaksiDTO);
            }
        }
        model.addAttribute("listTransaksi", listTransaksiDTO);
        return  "transaction/view-transaction";
    }

    @GetMapping("/create")
    public String buatTransaksi(Model model){
        Transaksi transaksi = new Transaksi();
        model.addAttribute("transaksi", transaksi);
        return "transaction/create-transaction";
    }

    @PostMapping("/add")
    public String addTransaksi(@ModelAttribute Transaksi transaksi, Authentication authentication){
        UserModel user = userService.findByUsername(authentication.getName());
        transaksi.setWaktuTransaksi(LocalDateTime.now());
        transaksi.setCabang(user.getCabang());
        transaksiService.saveTransaksi(transaksi);
        return "redirect:/transaction/history";
    }
}
