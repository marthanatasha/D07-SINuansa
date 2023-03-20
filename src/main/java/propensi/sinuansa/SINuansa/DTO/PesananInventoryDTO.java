package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesananInventoryDTO {
    String namaItem;
    Long jumlah;
    Long hargaItem;
    Long hargaTotal;

    public PesananInventoryDTO(){
        this.namaItem = "";
        this.jumlah=0L;
        this.hargaItem = 0L;
        this.hargaTotal = 0L;
    }

    public PesananInventoryDTO(String namaItem, Long jumlah, Long hargaItem, Long hargaTotal){
        this.namaItem = namaItem;
        this.jumlah = jumlah;
        this.hargaItem = hargaItem;
        this.hargaTotal = hargaTotal;
    }
}
