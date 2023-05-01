package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;
import propensi.sinuansa.SINuansa.model.Inventory;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PesananInventoryDTO {

    String cabang;
    LocalDateTime waktu;
    List<EntryPesananInventoryDTO> entryPI;
    List<Inventory> listInventory;
    Long harga;
    boolean isKopi;


    public PesananInventoryDTO() {
        this.cabang = "";
        this.waktu = null;
        this.entryPI = null;
        this.listInventory = null;
        this.harga = 0L;
        this.isKopi = false;
    }

    public PesananInventoryDTO(String cabang, LocalDateTime waktu, List<EntryPesananInventoryDTO> entryPI, List<Inventory> listInventory,Long harga, boolean isKopi) {
        this.cabang = cabang;
        this.waktu = waktu;
        this.entryPI = entryPI;
        this.listInventory = listInventory;
        this.harga = harga;
        this.isKopi = isKopi;
    }

}
