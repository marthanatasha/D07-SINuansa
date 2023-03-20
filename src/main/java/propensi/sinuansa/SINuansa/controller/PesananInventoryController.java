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
@RequestMapping("/orderinventory")
public class PesananInventoryController {

    @Qualifier("pesananInventoryServiceImpl")
    @Autowired
    private PesananInventoryService pesananInventoryService;

    @Autowired
    private EntryPIService entryPIService;
    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/create")
    public String createOrderInventory(Model model) {
        PesananInventory pesananInventory = new PesananInventory();

        List<EntryPI> listEntryPInew = new ArrayList<>();
        List<Inventory> listInventory = inventoryService.getListInventory();

        pesananInventory.setEntryPIList(listEntryPInew);
        pesananInventory.getEntryPIList().add(new EntryPI());
        
        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("listInventory", listInventory);

        return "pesananInventory/form-create";
    }

    @PostMapping(value="/create", params={"addInventory"})
    private String addMultipleInventory(@ModelAttribute PesananInventory pesananInventory, Model model) {
        List<Inventory> listInventory = inventoryService.getListInventory();
        if (pesananInventory.getEntryPIList() == null) {
            pesananInventory.setEntryPIList(new ArrayList<>());
        }

        pesananInventory.getEntryPIList().add(new EntryPI());

        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("listInventory", listInventory);

        return "pesananInventory/form-create";
    }

    @PostMapping(value="/create", params={"save"})
    public String createOrderInventorySubmit(@ModelAttribute PesananInventory pesananInventory, Model model) {
        if (pesananInventory.getEntryPIList() == null) {
            pesananInventory.setEntryPIList((new ArrayList<>()));
        } else {
            List<EntryPI> entryPIList = pesananInventory.getEntryPIList();

            for (int i = 0; i < entryPIList.size(); i++) {
                EntryPI entry = entryPIList.get(i);
                entry.setPesananInventory(pesananInventory);
                entry.setInventory(entry.getInventory());
            }

            if (entryPIList.get(0).getInventory().isKopi() == true) {
                pesananInventory.setKopi(true);
            } else {
                pesananInventory.setKopi(false);
            }

            //another logic
            String prefix = "ORDER";
            String cabang = ""; //retrieve cabang dari user
            String noId = String.format("%03d", String.valueOf(pesananInventoryService.getListPesananInventory().size() - 1));

            String kode = cabang+"-"+prefix+"-"+noId;

            pesananInventory.setKode(kode);

            //retrieve status from order request
            pesananInventory.setStatus("On Progress");

            pesananInventoryService.addPesananInventory(pesananInventory);
            model.addAttribute("pesananInventory", pesananInventory);

        }
        return "home";
    }

    @GetMapping("/all")
    public String retrieveAllOrder(Model model) {
        List<PesananInventory> lst = pesananInventoryService.getListPesananInventory();
        model.addAttribute("listPesananInventory", lst);
        return "pesananInventory/all";
    }

    @GetMapping("/detail/{id}")
    public String detailPesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
        return "pesananInventory/detail";
    }

    @GetMapping("/confirm/{id}")
    public String confirmPesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
//        return "pesananInventory/confirm";
        return "home";
    }
}