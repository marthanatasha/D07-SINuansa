package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Supplier;
import propensi.sinuansa.SINuansa.service.SupplierService;

import java.util.List;

@Controller
public class SupplierController {
    @Qualifier("supplierServiceImpl")
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier/viewall")
    public String listSupplier(Model model){
        List<Supplier> listSupplier = supplierService.getListSupplier();
        model.addAttribute("listSupplier", listSupplier);
        return "supplier/viewall-supplier";
    }

    @GetMapping("/supplier/add")
    public String addSupplierForm(Model model){
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        return "supplier/form-add-supplier";
    }

    @PostMapping(value = "/supplier/add")
    public String addSupplierSubmit(@ModelAttribute Supplier supplier, Model model) {
//        dokterModel.setRole(Role.DOKTER);

        supplierService.addSupplier(supplier);
        model.addAttribute("supplier", supplier);
        List<Supplier> listSupplier = supplierService.getListSupplier();
        model.addAttribute("listDokter", listSupplier);
        return "redirect:/supplier/viewall";
    }

    @GetMapping("/supplier/delete/{id}")
    public String deleteSupplierSubmitPage(@PathVariable Long id, Model model){
        Supplier supplier = supplierService.findSupplierId(id);
        Supplier deleteSupplier = supplierService.deleteSupplier(supplier);
        model.addAttribute("id", supplier.getId());
        return "redirect:/supplier/viewall";

    }
}
