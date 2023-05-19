package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.sinuansa.SINuansa.model.*;
import propensi.sinuansa.SINuansa.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Qualifier("cabangServiceImpl")
    @Autowired
    private CabangService cabangService;

    @Qualifier("inventoryServiceImpl")
    @Autowired
    private InventoryService inventoryService;

    @Qualifier("transaksiServiceImpl")
    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    public List<Inventory> getListInventoryBasedOnType(Long isKopi, Authentication auth) {
        String username = auth.getName();
        UserModel user = userService.findByUsername(username);

        boolean pesananPabrik = false;
        if (isKopi.equals(Long.valueOf(1))) {
            pesananPabrik = true;
        }
        List<Inventory> listInventory = inventoryService.getListInventoryBasedOnType(pesananPabrik);

        List<Inventory> res = new ArrayList<>();

        for (Inventory i : listInventory) {
            if (i.getCabang().equals(user.getCabang())) {
                res.add(i);
            }
        }

        return res;
    }

    @GetMapping("/create")
    public String createOrder() {
        return "PesananInventory/create";
    }

    public String generatePin() {
        Random r = new Random(System.currentTimeMillis());
        return ""+(10000 + r.nextInt(20000));
    }

    @GetMapping("/create/{isKopi}")
    public String createOrderInventory(@PathVariable Long isKopi,
                                       Authentication auth,
                                       Model model) {
        String username = auth.getName();
        UserModel user = userService.findByUsername(username);
        PesananInventory pesananInventory = new PesananInventory();
        List<Inventory> inventoryList = getListInventoryBasedOnType(isKopi, auth);
        List<EntryPI> entryPIList = entryPIService.getListEntryPI();
        List<EntryPI> entryPIListNew = new ArrayList<>();

        pesananInventory.setEntryPIList(entryPIListNew);
        pesananInventory.getEntryPIList().add(new EntryPI());

        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("listInventory", inventoryList);
        model.addAttribute("entryPIList", entryPIList);
        model.addAttribute("isKopi", isKopi);
        return "PesananInventory/form-create";
    }

    @PostMapping(value="/create/{isKopi}", params={"save"})
    public String createOrderInventorySubmit (@ModelAttribute PesananInventory pesananInventory,
                                              @PathVariable Long isKopi,
                                              Authentication auth,
                                              Model model) {
        String username = auth.getName();
        UserModel user = userService.findByUsername(username);

        Long total_harga = 0L;
        //todo: authorities
        if (pesananInventory.getEntryPIList() == null) {
            pesananInventory.setEntryPIList(new ArrayList<>());
        } else {
            for (int i = 0; i < pesananInventory.getEntryPIList().size() ; i++) {
                EntryPI entryPI = pesananInventory.getEntryPIList().get(i);

                entryPI.setPesananInventory(pesananInventory);
                entryPI.setInventory(pesananInventory.getEntryPIList().get(i).getInventory());

                total_harga += entryPI.getKuantitas() * Long.valueOf(entryPI.getHarga());
            }
        }

        boolean flag = true;
        if (isKopi.equals(0L)) {
            flag = false;
        }
        //auth: retrieve Cabang information from authentication
        Cabang cabang = user.getCabang();
        pesananInventory.setCabang(cabang);
        pesananInventory.setKopi(flag);

        //todo: Set for default pesananInventory
        String prefix = "ORDER";
        String cabangKode = "";

        if (cabang.getId().equals(1L)) {
            cabangKode = "A";
        } else if (cabang.getId().equals(2L)) {
            cabangKode = "B";
        } else {
            cabangKode = "C";
        }

        String noId = String.format("%03d", (pesananInventoryService.getListPesananInventory().size() + 1));
        String kode = cabangKode + "-" + prefix + "-" + noId; //todo: ini masih error

        pesananInventory.setKode(kode);
        pesananInventory.setStatus("Waiting for Manager Approval");
        pesananInventory.setPin("-");
        pesananInventory.setWaktuPemesanan(LocalDateTime.now());
        pesananInventory.setHarga(total_harga);

        //todo: create new transaction

        pesananInventoryService.addPesananInventory(pesananInventory);

        List<PesananInventory> lst = pesananInventoryService.getListPesananInventory();
        model.addAttribute("listPesananInventory", lst);
        return "redirect:/orderinventory/all";
    }

    @PostMapping(value="/create/{isKopi}", params={"addItem"})
    private String addRowInventory(@ModelAttribute PesananInventory pesananInventory,
                                   @PathVariable Long isKopi,
                                   Authentication auth,
                                   Model model) {
        String username = auth.getName();
        UserModel user = userService.findByUsername(username);
        List<Inventory> listInventory = getListInventoryBasedOnType(isKopi, auth);

        if (pesananInventory.getEntryPIList() == null || pesananInventory.getEntryPIList().size() == 0) {
            pesananInventory.setEntryPIList(new ArrayList<>());
        }
        pesananInventory.getEntryPIList().add(new EntryPI());
        List<EntryPI> entryPIList = entryPIService.getListEntryPI();

        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("entryPIListExisting", entryPIList);
        model.addAttribute("listInventory", listInventory);

        return "PesananInventory/form-create";
    }

    @PostMapping(value="/create/{isKopi}", params={"deleteItem"})
    private String deleteRowInventory(@ModelAttribute PesananInventory pesananInventory,
                                      @RequestParam("deleteItem") Integer row,
                                      @PathVariable Long isKopi,
                                      Authentication auth,
                                      Model model) {
        String username = auth.getName();
        UserModel user = userService.findByUsername(username);
        List<Inventory> listInventory = getListInventoryBasedOnType(isKopi, auth);

        final Integer rowInt = Integer.valueOf(row);
        pesananInventory.getEntryPIList().remove(rowInt.intValue());

        List<EntryPI> entryPIList = pesananInventory.getEntryPIList();

        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("entryPIListExisting", entryPIList);
        model.addAttribute("listInventory", listInventory);

        return "PesananInventory/form-create";
    }

    @GetMapping("/all")
    public String retrieveAllOrder(Model model, Authentication auth) {
        String username = auth.getName();
        UserModel user = userService.findByUsername(username);

        List<PesananInventory> lst = pesananInventoryService.getListPesananInventory();
        List<PesananInventory> res = new ArrayList<>();

        for (PesananInventory i : lst) {
            if (i.getCabang().equals(user.getCabang())) {
                res.add(i);
            }
        }

        model.addAttribute("listPesananInventory", res);
        return "PesananInventory/all";
    }

    @GetMapping("/detail/{id}")
    public String detailPesananInventory(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);
        boolean flag = true;

        if (!pesananInventory.getStatus().equalsIgnoreCase("On Process")) {
            flag = false;
        }

        model.addAttribute("flag", flag);
        model.addAttribute("pesananInventory", pesananInventory);
        if (pesananInventory.isKopi()) {
            return "PesananInventory/detailKopi";
        } else {
            return "PesananInventory/detailNonKopi";
        }
    }

    @GetMapping("/supplier/{id}")
    public String retrieveSupplierInventory(@PathVariable Long id, Model model) {
        EntryPI entry = entryPIService.findEntryPIId(id);
        PesananInventory pesananInventory = entry.getPesananInventory();
        List<Supplier> supplierList = supplierService.getListSupplier();
        List<Supplier> result = new ArrayList<>();
        for (Supplier i : supplierList) {
            //todo: sementara masih ambil dari materials cuma lagi diusahain pake id inventory (udah pake inventory nanti coba lagi)
            if (i.getInventory().equals(entry.getInventory()) && i.getStatus().equalsIgnoreCase("Active")) {
                result.add(i);
            }
        }
        model.addAttribute("inventory", entry.getInventory());
        model.addAttribute("supplierList", result);
        model.addAttribute("pesananInventory", pesananInventory);
        return "PesananInventory/supplierList";
    }

    @GetMapping("/update/{id}")
    public String updateStatusNonCoffee(@PathVariable Long id, Model model) {
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);
        model.addAttribute("pesananInventory", pesananInventory);
        return "PesananInventory/updateStatusNonKopi";
    }

    @PostMapping(value="/update/{id}", params={"update"})
    public String updateStatusNonCoffeeSubmit(@PathVariable Long id, Model model, Authentication authentication) {
        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(id);

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
        tr.setCabang(userService.findByUsername(authentication.getName()).getCabang());
        transaksiService.saveTransaksi(tr);

        //Set done
        pesananInventory.setTransaksi(tr);
        pesananInventory.setStatus("Done");
        pesananInventoryService.updatePesananInventory(pesananInventory);

        boolean flag = true;

        if (!pesananInventory.getStatus().equalsIgnoreCase("On Process")) {
            flag = false;
        }
        model.addAttribute("flag", flag);
        return "redirect:/orderinventory/detail/" + id;
    }
}