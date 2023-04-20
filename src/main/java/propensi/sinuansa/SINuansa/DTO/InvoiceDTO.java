package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InvoiceDTO {

    String namaCabang;
    String alamatCabang;
    String noCabang;
    LocalDateTime waktu;
    String noPembayaran;
    List<ItemDTO> items;
    String source;
    Long harga;


    public InvoiceDTO(){
        this.namaCabang = "";
        this.alamatCabang = "";
        this.noCabang= "";
        this.waktu= null;
        this.noPembayaran="";
        this.items=null;
        this.source="";
        this.harga=0L;
    }

    public InvoiceDTO(String namaCabang, String alamatCabang, String noCabang, LocalDateTime waktu, String noPembayaran, List<ItemDTO> item, String source, Long harga){
        this.namaCabang = namaCabang;
        this.alamatCabang = alamatCabang;
        this.noCabang = noCabang;
        this.waktu = waktu;
        this.noPembayaran = noPembayaran;
        this.items = item;
        this.source = source;
        this.harga = harga;
    }

}
