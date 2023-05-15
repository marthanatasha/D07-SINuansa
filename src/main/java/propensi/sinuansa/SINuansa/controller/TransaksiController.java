package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.sinuansa.SINuansa.DTO.TransaksiDTO;
import propensi.sinuansa.SINuansa.model.Transaksi;
import propensi.sinuansa.SINuansa.service.TransaksiService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransaksiController {
    @Autowired
    TransaksiService transaksiService;

    @GetMapping("/history")
    public String lihatTransaksi(Model model){
        List<Transaksi> listTransaksi = transaksiService.getTransaksiList();
        List<TransaksiDTO> listTransaksiDTO = new ArrayList<TransaksiDTO>();
        for(Transaksi transaksi : listTransaksi){
            TransaksiDTO transaksiDTO = new TransaksiDTO(transaksi.getId(), transaksi.getNama(), transaksi.getAkun(), transaksi.getRefCode(), transaksi.getKategori(),
                    transaksi.getNominal(), transaksi.getKuantitas(), transaksi.getWaktuTransaksi());
            listTransaksiDTO.add(transaksiDTO);
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
    public String addTransaksi(@ModelAttribute Transaksi transaksi){
        transaksi.setWaktuTransaksi(LocalDateTime.now());
        transaksiService.saveTransaksi(transaksi);
        return "redirect:/transaction/history";
    }
}
