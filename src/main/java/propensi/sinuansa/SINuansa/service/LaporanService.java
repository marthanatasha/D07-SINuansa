package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.DTO.BarChartDTO;
import propensi.sinuansa.SINuansa.DTO.LaporanCollectionDTO;
import propensi.sinuansa.SINuansa.DTO.LineChartDTO;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Laporan;

public interface LaporanService {
    Laporan findLaporanId(Long Id);
    Laporan getLaporan(int bulan, int tahun, Cabang cabang);
    LaporanCollectionDTO getLaporanOrganized(Laporan laporan, String bulan, String tahun);
    LaporanCollectionDTO calculateReport(LaporanCollectionDTO laporan);
    LineChartDTO getLineChart(int bulan, int tahun, Laporan laporan);
    BarChartDTO getBarChart(int bulan, int tahun);
}
