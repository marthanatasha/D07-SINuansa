package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.sinuansa.SINuansa.DTO.EntryPesananInventoryDTO;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.DTO.PesananInventoryDTO;
import propensi.sinuansa.SINuansa.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/approval")
public class RequestApprovalController {

    @Qualifier("pesananInventoryServiceImpl")
    @Autowired
    private PesananInventoryService pesananInventoryService;

    @Qualifier("entryPIServiceImpl")
    @Autowired
    private EntryPIService entryPIService;

    @Qualifier("supplierServiceImpl")
    @Autowired
    private SupplierService supplierService;

    @Qualifier("cabangServiceImpl")
    @Autowired
    private CabangService cabangService;

    @Qualifier("inventoryServiceImpl")
    @Autowired
    private InventoryService inventoryService;


    public String generatePin() {
        Random r = new Random(System.currentTimeMillis());
        return ""+(10000 + r.nextInt(20000));
    }

    @GetMapping("/all")
    public String viewAllRequest(Model model) {
        List<PesananInventory> listPesananInventory = pesananInventoryService.getListPesananInventory();
        List<PesananInventory> result = new ArrayList<>();

        for (PesananInventory pi : listPesananInventory) {
            if (pi.getStatus().equals("Waiting for Manager Approval")) {
                result.add(pi);
            }
        }
        model.addAttribute("listPesananInventory", result);
        return "ApprovalRequest/all";
    }

    @GetMapping("/detail/{id}")
    public String detailPesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);
        boolean finished = false;
        if(!pesananInventory.getStatus().equals("Waiting for Manager Approval")){
            finished=true;
        }
        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("finished", finished);
        return "ApprovalRequest/detail";
    }

    @GetMapping("/update/{id}")
    public String updatePesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
        return "ApprovalRequest/update";
    }

    @PostMapping(value="/update/{id}", params={"approved"})
    public String approveRequest(@ModelAttribute PesananInventory pesananInventory,
                                 @PathVariable Long id,
                                 Model model) {
        pesananInventory.setStatus("On Process");
        pesananInventory.setPin(generatePin());
        //todo: set transaksi
//        pesananInventory.setTransaksi(new Transaksi());
        pesananInventoryService.updatePesananInventory(pesananInventory);
        boolean finished = false;
        if(!pesananInventory.getStatus().equals("Waiting for Manager Approval")){
            finished=true;
        }
        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("finished", finished);
        return "redirect:/approval/detail/{id}";
    }

    @PostMapping(value="/update/{id}", params={"decline"})
    public String declineRequest(@ModelAttribute PesananInventory pesananInventory,
                                 @PathVariable Long id,
                                 Model model) {
        pesananInventory.setStatus("Declined");
        pesananInventory.setPin("-");
        pesananInventoryService.updatePesananInventory(pesananInventory);

        boolean finished = false;
        if(!pesananInventory.getStatus().equals("Waiting for Manager Approval")){
            finished=true;
        }
        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("finished", finished);
        return "redirect:/approval/detail/{id}";
    }
}