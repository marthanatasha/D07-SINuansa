package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.sinuansa.SINuansa.DTO.BarChartDTO;
import propensi.sinuansa.SINuansa.DTO.LaporanCollectionDTO;
import propensi.sinuansa.SINuansa.DTO.LineChartDTO;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Laporan;
import propensi.sinuansa.SINuansa.model.UserModel;
import propensi.sinuansa.SINuansa.service.CabangService;
import propensi.sinuansa.SINuansa.service.LaporanService;
import propensi.sinuansa.SINuansa.service.TransaksiService;
import propensi.sinuansa.SINuansa.service.UserService;

import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequestMapping("/report")
public class LaporanController {

    @Autowired
    LaporanService laporanService;

    @Autowired
    TransaksiService transaksiService;

    @Autowired
    UserService userService;

    @Autowired
    CabangService cabangService;

    @GetMapping("/A")
    public String viewLaporan(Model model){
        return "";
    }

    @GetMapping("")
    public String getLaporan (Model model){
        LocalDateTime today = LocalDateTime.now();
        ArrayList<Integer> yearList = new ArrayList<>();
        for(int i = 2020; i<= today.getYear(); i++){
            yearList.add(i);
        }
        String[] monthList = new DateFormatSymbols().getMonths();
        model.addAttribute("monthList", monthList);
        model.addAttribute("yearList", yearList);
        return "laporan/view-laporan";
    }

    @RequestMapping(value = "/{bulan}/{tahun}")
    public String laporanPDF(Model model, @PathVariable int bulan, @PathVariable int tahun, Authentication authentication
                             ){
        UserModel user = userService.findByUsername(authentication.getName());
        Cabang cabang = user.getCabang();
        Laporan laporan = laporanService.getLaporan(bulan, tahun, cabang);
        String bulanLaporan = laporan.getWaktuLaporan().getMonth().name();
        String tahunLaporan = "" + laporan.getWaktuLaporan().getYear();
        transaksiService.getTransaksiLaporanList(bulan, tahun, laporan, cabang);
        LaporanCollectionDTO laporanList = laporanService.getLaporanOrganized(laporan, bulanLaporan, tahunLaporan);
        LaporanCollectionDTO laporanDTO = laporanService.calculateReport(laporanList);
        LocalDateTime today = LocalDateTime.now();
        ArrayList<Integer> yearList = new ArrayList<>();
        for(int i = 2020; i<= today.getYear(); i++){
            yearList.add(i);
        }
        String[] monthList = new DateFormatSymbols().getMonths();
        LineChartDTO lineChart = laporanService.getLineChart(bulan, tahun, laporan);
        BarChartDTO barChart = laporanService.getBarChart(bulan, tahun, cabang);
        model.addAttribute("laporanDTO", laporanDTO);
        model.addAttribute("monthList", monthList);
        model.addAttribute("yearList", yearList);
        model.addAttribute("lineChart", lineChart);
        model.addAttribute("barChart", barChart);
        return "laporan/view-laporan";
    }
}
