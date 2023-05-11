package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Inventory;
import propensi.sinuansa.SINuansa.model.Supplier;
import propensi.sinuansa.SINuansa.repository.SupplierDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService{
    @Autowired
    SupplierDb supplierDb;

    @Override
    public Supplier findSupplierId(Long id){
        Optional<Supplier> supplier = supplierDb.findById(id);
        if(supplier.isPresent()){
            return supplier.get();
        }else return null;
    }

    @Override
    public void addSupplier(Supplier supplier){
        supplierDb.save(supplier);
    }

    @Override
    public List<Supplier> getListSupplier(){
        return supplierDb.findAll();
    }

    @Override
    public List<Supplier> getListSupplierByCabang(Cabang cabang) {
        return supplierDb.findAllByCabang(cabang);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier){
        supplierDb.save(supplier);
        return supplier;
    }

    @Override
    public Supplier deleteSupplier(Supplier supplier){
        supplierDb.delete(supplier);
        return supplier;
    }
}
