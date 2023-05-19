package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransaksiDTO {
    Long id;
    String nama;
    String akun;
    String refCode;
    String kategori;
    Long nominal;
    Long kuantitas;
    LocalDateTime waktuTransaksi;

    public TransaksiDTO(){
        this.id = 0L;
        this.nama = "";
        this.akun = "";
        this.refCode = "";
        this.kategori = "";
        this.nominal = 0L;
        this.kuantitas = 0L;
        this.waktuTransaksi = null;
    }

    public TransaksiDTO(Long id, String nama, String akun, String refCode, String kategori, Long nominal, Long kuantitas, LocalDateTime waktuTransaksi){
        this.id = id;
        this.nama = nama;
        this.akun = akun;
        this.refCode = refCode;
        this.kategori = kategori;
        this.nominal = nominal;
        this.kuantitas = kuantitas;
        this.waktuTransaksi = waktuTransaksi;
    }
}
