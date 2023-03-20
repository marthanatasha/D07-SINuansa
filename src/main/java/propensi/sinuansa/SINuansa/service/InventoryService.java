package propensi.sinuansa.SINuansa.service;
import propensi.sinuansa.SINuansa.model.Inventory;
import java.util.List;

public interface InventoryService {
    void addInventory (Inventory inventory);
    Inventory updateInventory (Inventory inventory);
    Inventory deleteInventory (Inventory inventory);
    Inventory getInventoryById (Long id);
    List<Inventory> getListInventory();
}
