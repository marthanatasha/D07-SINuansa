package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.sinuansa.SINuansa.model.PesananInventory;
import propensi.sinuansa.SINuansa.service.InventoryService;
import propensi.sinuansa.SINuansa.service.PesananInventoryService;
import propensi.sinuansa.SINuansa.service.PesananPabrikService;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/all")
    private String getPesananPabrik(Model model) {
        List<PesananInventory> allList = pesananPabrikService.getListPesanan();
        model.addAttribute("listPesanan", allList);
        return "pesananPabrik/all";
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
        return "pesananPabrik/all";
    }

    @GetMapping("/detail/{id}")
    public String detailPesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananPabrikService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
        return "pesananPabrik/detail";
    }

    //Update Pesanan Pabrik --> Update Status
    @GetMapping("/update/{id}")
    public String updatePesananInventory(@PathVariable Long id, Model model) {
        String input = "";
        PesananInventory pesananInventory = pesananPabrikService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("input", input);
        return "pesananPabrik/update";
    }

    @RequestMapping(value="/updateStatus/{input}")
    @ResponseBody
    public String updateStatusPesanan(@PathVariable String input,
                                      @ModelAttribute PesananInventory pesananInventory,
                                      Model model) {
        String pin_pesanan = pesananInventory.getPin();
        Long id = pesananInventory.getId();
        System.out.println(pin_pesanan);
        System.out.println(input);

        if (pin_pesanan.equals(input)) {
            pesananInventory.setStatus("Done");
            pesananInventoryService.updatePesananInventory(pesananInventory);
        } else {
            model.addAttribute("err_msg", "PIN doesn't match. Please try again!");
        }
        return "redirect:/orderfactory/detail/" + id;
    }
}
