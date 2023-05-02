package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaporanDTO {

    String ref;
    String kategori;
    Long total;

    public LaporanDTO(){
        this.ref = null;
        this.kategori = null;
        this.total = 0L;
    }

    public LaporanDTO(String ref, String kategori, Long total){
        this.ref = ref;
        this.kategori = kategori;
        this.total = total;
    }
}
