package propensi.sinuansa.SINuansa.service;
import org.springframework.data.relational.core.sql.In;
import propensi.sinuansa.SINuansa.model.Inventory;
import java.util.List;

public interface InventoryService {
    void addInventory (Inventory inventory);
    Inventory updateInventory (Inventory inventory);
    Inventory deleteInventory (Inventory inventory);
    Inventory getInventoryById (Long id);
    Inventory getInventoryByNama(String nama);
    List<Inventory> getListInventory();
    List<Inventory> getListInventoryBasedOnType(boolean isKopi);
    List<Inventory> getListInventoryByCabang(String cabang);
}
