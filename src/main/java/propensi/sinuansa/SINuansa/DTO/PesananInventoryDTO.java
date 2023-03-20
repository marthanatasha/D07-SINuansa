package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PesananInventoryDTO {

    String cabang;
    LocalDateTime waktu;
    List<EntryPesananInventoryDTO> entryPI;
    Long harga;


    public PesananInventoryDTO() {
        this.cabang = "";
        this.waktu= null;
        this.entryPI=null;
        this.harga=0L;
    }

    public PesananInventoryDTO(String cabang, LocalDateTime waktu, List<EntryPesananInventoryDTO> entryPI, Long harga) {
        this.cabang = cabang;
        this.waktu = waktu;
        this.entryPI = entryPI;
        this.harga = harga;
    }

}
