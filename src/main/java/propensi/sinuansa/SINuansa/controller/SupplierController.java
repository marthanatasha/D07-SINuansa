package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Supplier;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.service.InventoryService;
import propensi.sinuansa.SINuansa.service.SupplierService;
import propensi.sinuansa.SINuansa.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SupplierController {
    @Qualifier("supplierServiceImpl")
    @Autowired
    private SupplierService supplierService;

    @Autowired
    UserService userService;

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/supplier/viewall")
    public String listSupplier(Model model, Authentication authentication){
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        List<Supplier> listSupplier = supplierService.getListSupplierByCabang(user.getCabang());
        model.addAttribute("listSupplier", listSupplier);
        return "supplier/viewall-supplier";
    }

    @GetMapping("/supplier/add")
    public String addSupplierForm(Model model, Authentication authentication){
        Supplier supplier = new Supplier();
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        String cabang = user.getCabang().getNama();
        //listInvent
        List<Inventory> listInventory = inventoryService.getInventoryByCabangQuery(user.getCabang());
        model.addAttribute("supplier", supplier);
        model.addAttribute("cabang", cabang);
        model.addAttribute("listInventory", listInventory);
        return "supplier/form-add-supplier";
    }

    @PostMapping(value = "/supplier/add")
    public String addSupplierSubmit(@ModelAttribute Supplier supplier, Model model, Authentication authentication) {
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        supplier.setCabang(user.getCabang());
        supplier.setMaterial(supplier.getInventory().getNama());
        supplierService.addSupplier(supplier);
        model.addAttribute("supplier", supplier);

        List<Supplier> listSupplier = supplierService.getListSupplier();
        model.addAttribute("listSupplier", listSupplier);
        return "redirect:/supplier/viewall";
    }

    @RequestMapping("/supplier/delete")
    public String deleteSupplierSubmitPage(Model model,
                                           @RequestParam(value="temp",required = false) Long temp){
        Supplier supplier = supplierService.findSupplierId(temp);
        Supplier deleteSupplier = supplierService.deleteSupplier(supplier);
        model.addAttribute("id", supplier.getId());
        return "redirect:/supplier/viewall";

    }

    @GetMapping("/supplier/update/{id}")
    public String updateSupplierFormPage(@PathVariable Long id, Model model, Authentication authentication){
        Supplier supplier = supplierService.findSupplierId(id);
        //cabang
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);
        //listInvent
        List<Inventory> listInventory = inventoryService.getInventoryByCabangQuery(user.getCabang());
        model.addAttribute("listInventory", listInventory);
        model.addAttribute("supplier", supplier);


        return "supplier/form-update-supplier";
    }

    @PostMapping("/supplier/update")
    public String updateSupplierSubmit(@ModelAttribute Supplier supplier, Model model, Authentication authentication){
        //cabang
        String authorities = String.valueOf(authentication.getAuthorities().stream().toArray()[0]);
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        supplier.setCabang(user.getCabang());
        supplier.setMaterial(supplier.getInventory().getNama());
        Supplier updateSupplier = supplierService.updateSupplier(supplier);

        return "redirect:/supplier/viewall";
    }
}
