package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.sinuansa.SINuansa.DTO.EntryPesananInventoryDTO;
import propensi.sinuansa.SINuansa.model.EntryPI;
import propensi.sinuansa.SINuansa.model.Supplier;
import propensi.sinuansa.SINuansa.model.PesananInventory;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.DTO.PesananInventoryDTO;
import propensi.sinuansa.SINuansa.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orderinventory")
public class PesananInventoryController {

    @Qualifier("pesananInventoryServiceImpl")
    @Autowired
    private PesananInventoryService pesananInventoryService;

    @Qualifier("entryPIServiceImpl")
    @Autowired
    private EntryPIService entryPIService;

    @Qualifier("supplierServiceImpl")
    @Autowired
    private SupplierService supplierService;

    @Qualifier("inventoryServiceImpl")
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/create")
    public String createOrder() {
        return "pesananInventory/create";
    }
    @GetMapping("/create/{isKopi}")
    public String createOrderInventory(@PathVariable Long isKopi, Model model) {
        PesananInventoryDTO pesananInventoryDTO = new PesananInventoryDTO();
        EntryPI newEntry = new EntryPI();

        boolean pesananPabrik = false;
        if (isKopi != null && isKopi.equals(1)) {
            pesananPabrik = true;
        }
        List<Inventory> listInventory = inventoryService.getListInventoryBasedOnType(pesananPabrik);

        pesananInventoryDTO.setListInventory(listInventory);
        pesananInventoryDTO.setKopi(pesananPabrik);
        pesananInventoryDTO.setEntryPI(new ArrayList<>());

        model.addAttribute("pesananInventoryDTO", pesananInventoryDTO);
        model.addAttribute("entryPI", newEntry);
        model.addAttribute("listInventory", listInventory);
        return "pesananInventory/form-create";
    }

    @PostMapping(value="/create", params={"addInventory"})
    private String addMultipleInventory(@ModelAttribute PesananInventoryDTO pesananInventoryDTO, Model model) {
        List<Inventory> listInventory = inventoryService.getListInventory();
        if (pesananInventoryDTO.getEntryPI() == null || pesananInventoryDTO.getEntryPI().size() == 0) {
            pesananInventoryDTO.setEntryPI(new ArrayList<>());
        }

        pesananInventoryDTO.getEntryPI().add(new EntryPesananInventoryDTO());
        List<EntryPI> listEntryPI = entryPIService.getListEntryPI();

        model.addAttribute("pesananInventoryDTO", pesananInventoryDTO);
        model.addAttribute("listEntryPIExisting", listEntryPI);
        model.addAttribute("listInventory", listInventory);

        return "pesananInventory/form-create";
    }

    @PostMapping(value="/create", params={"preview"})
    private String previewCart(@ModelAttribute PesananInventoryDTO pesananInventoryDTO, Model model) {
        List<EntryPesananInventoryDTO> listEntryPI = new ArrayList<>();

//        for (int i = 0; i < pesananInventoryDTO.getEntryPI().size(); i++) {
//            EntryPI newEntry = new EntryPI();
//            EntryPesananInventoryDTO entryPIDto= pesananInventoryDTO.getEntryPI().get(i);
//
//            newEntry.setNama(entryPIDto.getNamaInventory());
//            newEntry.setKuantitas(entryPIDto.getJumlah());
//            newEntry.setHarga(entryPIDto.getHargaItem().toString());
//
//            Inventory inv = inventoryService.getInventoryById(Long.valueOf(entryPIDto.getIdInventory()));
//            newEntry.setInventory(inv);
//
//            if (pesananInventoryDTO.isKopi() == false) {
//                newEntry.setSupplier(new Supplier()); //todo: ambil supplier dari setiap entry pesanan inventory non-kopi
//            }
//
//            listEntryPI.add(newEntry);
//        }

        for (EntryPesananInventoryDTO entry : pesananInventoryDTO.getEntryPI()) {
            entry.setNamaInventory(inventoryService.getInventoryById(Long.valueOf(entry.getIdInventory())).getNama());
            listEntryPI.add(entry);
        }


        model.addAttribute("pesananInventoryDTO", listEntryPI);
        return "pesananInventory/preview";
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
                entry.setInventory(pesananInventory.getEntryPIList().get(i).getInventory());
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

            String kode = cabang + "-" + prefix + "-" + noId;

            pesananInventory.setKode(kode);

            //retrieve status from order request
            pesananInventory.setStatus("Waiting for Manager Approval");

            pesananInventoryService.addPesananInventory(pesananInventory);
            model.addAttribute("pesananInventory", pesananInventory);
        }
        return "redirect:/orderinventory/all";
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