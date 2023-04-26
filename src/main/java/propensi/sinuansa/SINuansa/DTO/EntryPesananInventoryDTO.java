package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryPesananInventoryDTO {
    String namaInventory;
    String idInventory;
    Long jumlah;
    Long hargaItem;
    Long hargaTotal;

    public EntryPesananInventoryDTO(){
        this.namaInventory = "";
        this.idInventory = "";
        this.jumlah=0L;
        this.hargaItem = 0L;
        this.hargaTotal = 0L;
    }

    public EntryPesananInventoryDTO(String namaInventory, String idInventory, Long jumlah, Long hargaItem, Long hargaTotal){
        this.namaInventory = namaInventory;
        this.idInventory = idInventory;
        this.jumlah = jumlah;
        this.hargaItem = hargaItem;
        this.hargaTotal = hargaTotal;
    }
}
