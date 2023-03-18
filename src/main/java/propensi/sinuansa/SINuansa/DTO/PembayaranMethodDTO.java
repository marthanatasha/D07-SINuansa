package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PembayaranMethodDTO {

    String method;
    String source;
    Long price;

    public PembayaranMethodDTO(){
        this.method = "";
        this.source = "";
        this.price = null;
    }

    public PembayaranMethodDTO(String method, String source, Long price){
        this.method = method;
        this.source = source;
        this.price = price;
    }

}
