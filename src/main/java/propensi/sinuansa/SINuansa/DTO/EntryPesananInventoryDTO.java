package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;
import propensi.sinuansa.SINuansa.model.Inventory;

@Getter
@Setter
public class EntryPesananInventoryDTO {
    Inventory inventory;
    String namaInventory;
    String idInventory;
    Long jumlah;
    Long hargaItem;

    public EntryPesananInventoryDTO(){
        this.inventory = null;
        this.namaInventory = "";
        this.idInventory = "";
        this.jumlah = 0L;
        this.hargaItem = 0L;
    }

    public EntryPesananInventoryDTO(Inventory inventory, String namaInventory, String idInventory, Long jumlah, Long hargaItem, Long hargaTotal){
        this.inventory = inventory;
        this.namaInventory = namaInventory;
        this.idInventory = idInventory;
        this.jumlah = jumlah;
        this.hargaItem = hargaItem;
    }
}
