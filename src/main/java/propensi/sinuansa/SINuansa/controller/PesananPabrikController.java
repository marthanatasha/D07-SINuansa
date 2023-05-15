package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderfactory")
public class PesananPabrikController {

    @Qualifier("pesananPabrikServiceImpl")
    @Autowired
    private PesananPabrikService pesananPabrikService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PesananInventoryService pesananInventoryService;

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping("/all")
    private String getPesananPabrik(Model model) {
        List<PesananInventory> allList = pesananPabrikService.getListPesanan();
        model.addAttribute("listPesanan", allList);
        return "PesananPabrik/all";
    }

    @GetMapping("/{store}")
    private String getPesananPabrikByStore(@PathVariable String store, Model model) {
        List<PesananInventory> listStore = new ArrayList<>();
        int idStore = 0;

        if (store != null) {
            if (store.equalsIgnoreCase("1")) {
                idStore = 1;
            } else if (store.equalsIgnoreCase("2")) {
                idStore = 2;
            } else if (store.equalsIgnoreCase("3")) {
                idStore = 3;
            }
        }

        List<PesananInventory> allList = pesananPabrikService.getListPesanan();

        for (PesananInventory i : allList) {
            if ((idStore != 0) && (i.getCabang().getId() == Long.valueOf(idStore))) {
                listStore.add(i);
            }
        }

        model.addAttribute("listPesanan", listStore);
        return "PesananPabrik/all";
    }

    @GetMapping("/detail/{id}")
    public String detailPesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananPabrikService.findPesananInventoryId(id);
        boolean flag = true;

        if (pesananInventory.getStatus().equalsIgnoreCase("Done") || pesananInventory.getStatus().equalsIgnoreCase("Declined")) {
            flag = false;
        }
        model.addAttribute("flag", flag);
        model.addAttribute("pesananInventory", pesananInventory);
        return "PesananPabrik/detail";
    }

    //Update Pesanan Pabrik --> Update Status
    @GetMapping("/update/{id}")
    public String updatePesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananPabrikService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("id", id);
        return "PesananPabrik/update";
    }

    @PostMapping(value="/update/{id}", params={"update"})
    public String updateStatusPesanan(@PathVariable Long id,
                                      @RequestParam(value="inputPin", required = false) String inputPin,
                                      Model model) {

        //todo: ambil inputPin dari html ke controller inih
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);
        String pin_pesanan = pesananInventory.getPin();

        if (pin_pesanan.equals(inputPin)) {
            //Increment inventory
            List<EntryPI> entryPIList = pesananInventory.getEntryPIList();
            for(EntryPI entry : entryPIList) {
                Inventory x = entry.getInventory();
                int jumlahX = x.getJumlah();

                jumlahX += (int)(long)entry.getKuantitas();
                x.setJumlah(jumlahX);
            }

            //Create transaksi
            Transaksi tr = new Transaksi();
            tr.setRefCode("5-50500 Biaya Produksi");
            tr.setPesananInventory(pesananInventory);
            tr.setAkun("Credit");
            tr.setNama(pesananInventory.getKode());
            tr.setKuantitas(1L);
            tr.setWaktuTransaksi(LocalDateTime.now());
            tr.setNominal(pesananInventory.getHarga());
            tr.setKategori("Harga Pokok Penjualan");
            transaksiService.saveTransaksi(tr);

            //Set done
            pesananInventory.setStatus("Done");
            pesananInventoryService.updatePesananInventory(pesananInventory);

            boolean flag = true;

            if (pesananInventory.getStatus().equalsIgnoreCase("Done") || pesananInventory.getStatus().equalsIgnoreCase("Declined")) {
                flag = false;
            }

            model.addAttribute("flag", flag);
            return "redirect:/orderfactory/detail/" + id;
        } else {
            pesananInventory.setStatus("On Process");
            pesananInventoryService.updatePesananInventory(pesananInventory);
            model.addAttribute("error", "PIN doesn't match. Please try again!");
            model.addAttribute("pesananInventory", pesananInventory);
            model.addAttribute("id", id);
            return "PesananPabrik/update";
        }
    }
}
