package propensi.sinuansa.SINuansa.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import propensi.sinuansa.SINuansa.DTO.LaporanCollectionDTO;
import propensi.sinuansa.SINuansa.DTO.LaporanDTO;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Laporan;
import propensi.sinuansa.SINuansa.model.Transaksi;
import propensi.sinuansa.SINuansa.service.CabangService;
import propensi.sinuansa.SINuansa.service.LaporanService;
import propensi.sinuansa.SINuansa.service.TransaksiService;

import java.util.List;

@RestController
@RequestMapping("/report")
public class LaporanRestController {

    @Autowired
    LaporanService laporanService;

    @Autowired
    CabangService cabangService;

    @Autowired
    TransaksiService transaksiService;

    @RequestMapping(value = "/pdf/{bulan}/{tahun}")
    public LaporanCollectionDTO laporanPDF(Model model, @PathVariable int bulan, @PathVariable int tahun
    ){
        Cabang cabang = cabangService.findCabangId(1L);
        Laporan laporan = laporanService.getLaporan(bulan, tahun, cabang);
        List<Transaksi> transaksiList = (laporan.getTransaksiList()==null? transaksiService.getTransaksiLaporanList(3, 2023, laporan):
                laporan.getTransaksiList());
        String bulanLaporan = laporan.getWaktuLaporan().getMonth().name();
        String tahunLaporan = "" + laporan.getWaktuLaporan().getYear();
        LaporanCollectionDTO laporanList = laporanService.getLaporanOrganized(laporan, bulanLaporan, tahunLaporan);
        LaporanCollectionDTO laporanDTO = laporanService.calculateReport(laporanList);
//        LocalDateTime today = LocalDateTime.now();
//        ArrayList<Integer> yearList = new ArrayList<>();
//        for(int i = 2020; i<= today.getYear(); i++){
//            yearList.add(i);
//        }
//        String[] monthList = new DateFormatSymbols().getMonths();
//        model.addAttribute("laporanDTO", laporanDTO);
//        model.addAttribute("monthList", monthList);
//        model.addAttribute("yearList", yearList);
        for(LaporanDTO laporans : laporanList.getListTransaksi()){
            System.out.println(laporans.getRef() + " " + laporans.getTotal());
        }
        System.out.println(laporanDTO.getTotalPendapatanPenjualan());
        System.out.println(laporanDTO.getTotalHargaPokokPenjualan());
        System.out.println(laporanDTO.getLabaKotor());
        System.out.println(laporanDTO.getTotalBiaya());
        System.out.println(laporanDTO.getPendapatanBersihOperasional());
        System.out.println(laporanDTO.getTotalBiayaLainnya());
        System.out.println(laporanDTO.getPendapatanBersih());

        return laporanDTO;
    }
}
