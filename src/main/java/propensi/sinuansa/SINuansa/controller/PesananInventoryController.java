package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.parameters.P;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public List<Inventory> getListInventoryBasedOnType(Long isKopi) {
        boolean pesananPabrik = false;
        if (isKopi.equals(Long.valueOf(1))) {
            pesananPabrik = true;
        }
        List<Inventory> listInventory = inventoryService.getListInventoryBasedOnType(pesananPabrik);
        return listInventory;
    }

    @GetMapping("/create")
    public String createOrder() {
        return "pesananInventory/create";
    }

    public String generatePin() {
        Random r = new Random(System.currentTimeMillis());
        return ""+(10000 + r.nextInt(20000));
    }
//
//    @GetMapping("/create/{isKopi}")
//    public String createOrderInventory(@PathVariable Long isKopi,
//                                       Model model) {
//        PesananInventory newOrder = new PesananInventory();
//
//        List<EntryPI> listEntryPI = new ArrayList<>();
//
//        List<Inventory> listInventory = getListInventoryBasedOnType(isKopi);
//
//        newOrder.setEntryPIList(listEntryPI);
//        newOrder.getEntryPIList().add(new EntryPI());
//
//        model.addAttribute("isKopi", isKopi);
//        model.addAttribute("pesananInventory", newOrder);
//        model.addAttribute("listInventory", listInventory);
//        return "pesananInventory/form-create";
//    }
//
//    @GetMapping("/create/{orderId}/{isKopi}")
//    private String addMultipleInventory(@PathVariable Long isKopi,
//                                        @PathVariable Long orderId,
//                                        Model model) {
//        List<Inventory> listInventory = getListInventoryBasedOnType(isKopi);
//        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(orderId);
//        if (pesananInventory.getEntryPIList() == null) {
//            pesananInventory.setEntryPIList(new ArrayList<>());
//        }
//
//        pesananInventory.getEntryPIList().add(new EntryPI());
//
//        model.addAttribute("isKopi", isKopi);
//        model.addAttribute("entryPIList", pesananInventory.getEntryPIList());
//        model.addAttribute("pesananInventory", pesananInventory);
//        model.addAttribute("listInventory", listInventory);
//
//        return "pesananInventory/form-create";
//    }
//
////    @PostMapping(value="/create/{orderId}/{isKopi}", params={"addRow"})
////    private String addMultipleInventory(@PathVariable Long isKopi,
////                                        @ModelAttribute PesananInventory pesananInventory,
////                                        Model model) {
////        List<Inventory> listInventory = getListInventoryBasedOnType(isKopi);
////
////        if (pesananInventory.getEntryPIList() == null) {
////            pesananInventory.setEntryPIList(new ArrayList<>());
////        }
////
////        pesananInventory.getEntryPIList().add(new EntryPI());
////
////        model.addAttribute("isKopi", isKopi);
////        model.addAttribute("pesananInventory", pesananInventory);
////        model.addAttribute("listInventory", listInventory);
////
////        return "pesananInventory/preview";
////    }
//
//    @PostMapping(value="/create/{orderId}/{isKopi}", params={"deleteRow"})
//    private String deleteRowEntry(@PathVariable Long isKopi,
//                                  @PathVariable Long orderId,
//                                  @ModelAttribute PesananInventory pesananInventory,
//                                  @RequestParam("deleteRow") Integer row,
//                                  Model model
//    ) {
//        pesananInventory = pesananInventoryService.findPesananInventoryId(orderId);
//        final Integer rowId = Integer.valueOf(row);
//        pesananInventory.getEntryPIList().remove(rowId);
//
//        List<Inventory> listInventory = getListInventoryBasedOnType(isKopi);
//        List<EntryPI> entryPIList = pesananInventory.getEntryPIList();
//
////        List<Inventory> listInventory = getListInventoryBasedOnType(isKopi);
////        PesananInventory pesananInventory = pesananInventoryService.findPesananInventoryId(idOrder);
////
////        if (pesananInventory.getEntryPIList() == null) {
////            pesananInventory.setEntryPIList(new ArrayList<>());
////        }
////
////        pesananInventory.getEntryPIList().add(new EntryPI());
//
//        model.addAttribute("isKopi", isKopi);
//        model.addAttribute("entryPIList", entryPIList);
//        model.addAttribute("pesananInventory", pesananInventory);
//        model.addAttribute("listInventory", listInventory);
//
//        return "pesananInventory/preview";
//    }
//    @PostMapping(value="/create/{isKopi}", params={"preview"})
//    private String previewCart(@PathVariable Long isKopi,
//                               @ModelAttribute PesananInventory pesananInventory,
//                               Model model) {
////        for (int i = 0; i < pesananInventoryDTO.getEntryPI().size(); i++) {
////            EntryPI newEntry = new EntryPI();
////            EntryPesananInventoryDTO entryPIDto= pesananInventoryDTO.getEntryPI().get(i);
////
////            newEntry.setNama(entryPIDto.getNamaInventory());
////            newEntry.setKuantitas(entryPIDto.getJumlah());
////            newEntry.setHarga(entryPIDto.getHargaItem().toString());
////
////            Inventory inv = inventoryService.getInventoryById(Long.valueOf(entryPIDto.getIdInventory()));
////            newEntry.setInventory(inv);
////
////            if (pesananInventoryDTO.isKopi() == false) {
////                newEntry.setSupplier(new Supplier()); //todo: ambil supplier dari setiap entry pesanan inventory non-kopi
////            }
////
////            listEntryPI.add(newEntry);
////        }
////        if (pesananInventory.getEntryPIList() == null) {
////            pesananInventory.setEntryPIList(new ArrayList<>());
////        } else {
////            List<EntryPI> listEntry = pesananInventory.getEntryPIList();
////
////            for (int i = 0; i < listEntry.size(); i++) {
////                EntryPI entry = listEntry.get(i);
////                entry.setPesananInventory(pesananInventory);
////                entry.setInventory(listEntry.get(i).getInventory());
////                entry.setNama(listEntry.get(i).getInventory().getNama());
////            }
////        }
//        System.out.println(1);
//
//        List<EntryPI> entryPIList = pesananInventory.getEntryPIList();
////        if (entryPIList == null) {
////            pesananInventory.setEntryPIList(new ArrayList<>());
////        }
//
//        System.out.println(2);
//        pesananInventory.setCabang(cabangService.findCabangId(1L));
//        //pesananInventory.setStatus("Draft");
//
//        boolean flag = true;
//        if (isKopi.equals(0L)) {
//            flag = false;
//        }
//        System.out.println(3);
//
//        for (int i = 0; i < entryPIList.size(); i++) {
//            if (pesananInventory == null) {
//                System.out.println("AAAAAAAAAA");
//            } else {
//                System.out.println("BBBBBB");
//            }
//
//            EntryPI entry = entryPIList.get(i);
//            entry.setPesananInventory(addedOrder);
//
//            Inventory inv = inventoryService.getInventoryById(entry.getInventory().getId());
//            System.out.println(inv);
//            entry.setInventory(inv);
//            entry.setNama(entryPIList.get(i).getInventory().getNama());
//        }
//        //dummy
//        pesananInventory.setKopi(flag);
//        System.out.println("3.2");
//        pesananInventory.setWaktuPemesanan(LocalDateTime.now());
//        System.out.println("3.3");
//        pesananInventory.setPin(generatePin());
//        System.out.println("3.4");
//        pesananInventory.setKode("");
//        System.out.println("3.5");
//        pesananInventory.setHarga(0L);
//        System.out.println("3.6");
//        pesananInventory.setStatus("");
//        System.out.println("3.7");
//        PesananInventory addedOrder = pesananInventoryService.addPesananInventory(pesananInventory);
//
//        System.out.println(4);
//
//
//
//
//        model.addAttribute("isKopi", isKopi);
//        model.addAttribute("pesananInventory", addedOrder);
//        model.addAttribute("entryPIList", entryPIList);
//        return "pesananInventory/preview";
//    }
//
//    @PostMapping(value="/create/{orderId}/{isKopi}", params={"save"})
//    public String createOrderInventorySubmit(@PathVariable Long isKopi,
//                                             @PathVariable Long orderId,
//                                             @ModelAttribute PesananInventory pesananInventory,
//                                             Model model) {
//        if (pesananInventory.getEntryPIList() == null) {
//            pesananInventory.setEntryPIList(new ArrayList<>());
//        }
//
//        //Set for default pesananInventory
//        String prefix = "ORDER";
//        String cabang = "A"; //todo: retrieve cabang dari user
//        String noId = String.format("%03d", (pesananInventoryService.getListPesananInventory().size() - 1));
//        String kode = cabang + "-" + prefix + "-" + noId;
//
//        pesananInventory.setKode(kode);
//        pesananInventory.setStatus("Waiting for Manager Approval");
//        pesananInventory.setKopi(true);
//
//        Long totalHarga = 0L;
//        for (EntryPI entry : pesananInventory.getEntryPIList()) {
//            Long pricePerEntry = Long.valueOf(entry.getHarga()) * entry.getKuantitas();
//            totalHarga += pricePerEntry;
//        }
//        pesananInventory.setHarga(totalHarga);
//        pesananInventoryService.updatePesananInventory(pesananInventory);
//
//        //fix bugs - delete dummy
//        List<PesananInventory> allLst = pesananInventoryService.getListPesananInventory();
//        for (PesananInventory pi : allLst) {
//            if (pi.getKode().equals("")) {
//                pesananInventoryService.deletePesananInventory(pi);
//            }
//        }
//
//        model.addAttribute("pesananInventory", pesananInventory);
//        return "redirect:/orderinventory/all";
//    }

    @GetMapping("/create/{isKopi}")
    public String createOrderInventory(@PathVariable Long isKopi,
                                       Model model) {
        PesananInventory pesananInventory = new PesananInventory();
        List<Inventory> inventoryList = inventoryService.getListInventory();
        List<EntryPI> entryPIList = entryPIService.getListEntryPI();
        List<EntryPI> entryPIListNew = new ArrayList<>();

        pesananInventory.setEntryPIList(entryPIListNew);
        pesananInventory.getEntryPIList().add(new EntryPI());

        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("listInventory", inventoryList);
        model.addAttribute("entryPIList", entryPIList);
        model.addAttribute("isKopi", isKopi);
        return "pesananInventory/create";
    }

    @PostMapping(value="/create/{isKopi}", params={"save"})
    public String createOrderInventorySubmit (@ModelAttribute PesananInventory pesananInventory,
                                              @PathVariable Long isKopi,
                                              Model model) {
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

        pesananInventory.setCabang(cabangService.findCabangId(1L));
        pesananInventory.setKopi(flag);

        //Set for default pesananInventory
        String prefix = "ORDER";
        String cabang = "A"; //todo: retrieve cabang dari user (authentication)
        String noId = String.format("%03d", (pesananInventoryService.getListPesananInventory().size() - 1));
        String kode = cabang + "-" + prefix + "-" + noId;

        pesananInventory.setKode(kode);
        pesananInventory.setStatus("Waiting for Manager Approval");
        pesananInventory.setPin(generatePin());
        pesananInventory.setWaktuPemesanan(LocalDateTime.now());
        pesananInventory.setHarga(total_harga);

        //todo: create new transaction

        pesananInventoryService.addPesananInventory(pesananInventory);
        model.addAttribute("pesananInventory", pesananInventory);
        return "pesananInventory/all";
    }

    @PostMapping(value="/create/{isKopi}", params={"addItem"})
    private String addRowInventory(@ModelAttribute PesananInventory pesananInventory,
                                   @PathVariable Long isKopi,
                                   Model model) {
        List<Inventory> listInventory = inventoryService.getListInventory();

        if (pesananInventory.getEntryPIList() == null || pesananInventory.getEntryPIList().size() == 0) {
            pesananInventory.setEntryPIList(new ArrayList<>());
        }
        pesananInventory.getEntryPIList().add(new EntryPI());
        List<EntryPI> entryPIList = entryPIService.getListEntryPI();

        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("entryPIListExisting", entryPIList);
        model.addAttribute("listInventory", listInventory);

        return "pesananInventory/form-create";
    }

    @PostMapping(value="/create/{isKopi}", params={"deleteItem"})
    private String deleteRowInventory(@ModelAttribute PesananInventory pesananInventory,
                                      @RequestParam("deleteItem") Integer row,
                                      @PathVariable Long isKopi,
                                      Model model) {
        List<Inventory> listInventory = inventoryService.getListInventory();

        final Integer rowInt = Integer.valueOf(row);
        pesananInventory.getEntryPIList().remove(rowInt.intValue());

        List<EntryPI> entryPIList = pesananInventory.getEntryPIList();

        model.addAttribute("pesananInventory", pesananInventory);
        model.addAttribute("entryPIListExisting", entryPIList);
        model.addAttribute("listInventory", listInventory);

        return "pesananInventory/form-create";
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