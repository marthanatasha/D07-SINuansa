package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier findSupplierId(Long Id);

    void addSupplier(Supplier supplier);

    List<Supplier> getListSupplier();

    Supplier updateSupplier(Supplier supplier);

    Supplier deleteSupplier (Supplier supplier);

}
