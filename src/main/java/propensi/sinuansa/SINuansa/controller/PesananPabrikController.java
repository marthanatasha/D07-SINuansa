package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.sinuansa.SINuansa.model.EntryPI;
import propensi.sinuansa.SINuansa.model.Pembayaran;
import propensi.sinuansa.SINuansa.model.Supplier;
import propensi.sinuansa.SINuansa.model.PesananInventory;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ordermanufacturer")
public class PesananPabrikController {

    @Qualifier("pesananPabrikServiceImpl")
    @Autowired
    private PesananPabrikService pesananPabrikService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PesananInventoryService pesananInventoryService;

    @GetMapping("/all")
    private String getPesananPabrik(Model model) {
        List<PesananInventory> allList = pesananPabrikService.getListPesanan();
        model.addAttribute("listPesanan", allList);
        return "/";
    }

    @GetMapping("/{store}")
    private String getPesananPabrikByStore(@PathVariable String store, Model model) {
        List<PesananInventory> listStore = new ArrayList<>();
        int idStore = 0;

        if (store != null) {
            if (store.equalsIgnoreCase("store a")) {
                idStore = 1;
            } else if (store.equalsIgnoreCase("store b")) {
                idStore = 2;
            } else if (store.equalsIgnoreCase("store c")) {
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
        return "/";
    }

    @GetMapping("/detail/{id}")
    public String detailPesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananPabrikService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
        return "/";
    }

    //Update Pesanan Pabrik --> Update Status
}
