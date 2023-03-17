package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Supplier;
import propensi.sinuansa.SINuansa.repository.SupplierDb;

import javax.transaction.Transactional;
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
}
