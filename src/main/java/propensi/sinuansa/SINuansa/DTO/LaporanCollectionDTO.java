package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LaporanCollectionDTO {

    List<LaporanDTO> listTransaksi;
    Long totalPendapatanPenjualan;
    Long totalHargaPokokPenjualan;
    Long labaKotor;
    Long totalBiaya;
    Long pendapatanBersihOperasional;
    Long totalBiayaLainnya;
    Long pendapatanBersih;
    String bulan;
    String tahun;
    String cabang;


    public LaporanCollectionDTO(){
        this.listTransaksi = new ArrayList<LaporanDTO>();
        this.totalPendapatanPenjualan =0L;
        this.totalHargaPokokPenjualan =0L;
        this.labaKotor=0L;
        this.totalBiaya=0L;
        this.pendapatanBersihOperasional=0L;
        this.totalBiayaLainnya=0L;
        this.pendapatanBersih=0L;
        this.bulan=null;
        this.tahun=null;
        this.cabang=null;
    }

    public LaporanCollectionDTO(ArrayList<LaporanDTO> listLaporan,
                                Long totalPendapatanPenjualan,
                                Long totalHargaPokokPenjualan,
                                Long labaKotor,
                                Long totalBiaya,
                                Long pendapatanBersihOperasional,
                                Long totalBiayaLainnya,
                                Long pendapatanBersih,
                                String  bulan,
                                String tahun,
                                String cabang){
        this.listTransaksi = listLaporan;
        this.totalPendapatanPenjualan = totalPendapatanPenjualan;
        this.totalHargaPokokPenjualan = totalHargaPokokPenjualan;
        this.labaKotor= labaKotor;
        this.totalBiaya= totalBiaya;
        this.pendapatanBersihOperasional= pendapatanBersihOperasional;
        this.totalBiayaLainnya= totalBiayaLainnya;
        this.pendapatanBersih= pendapatanBersih;
        this.bulan = bulan;
        this.tahun = tahun;
        this.cabang = cabang;
    }
}
