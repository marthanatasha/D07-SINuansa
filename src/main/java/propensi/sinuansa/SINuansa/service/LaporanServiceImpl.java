package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.DTO.BarChartDTO;
import propensi.sinuansa.SINuansa.DTO.LaporanCollectionDTO;
import propensi.sinuansa.SINuansa.DTO.LaporanDTO;
import propensi.sinuansa.SINuansa.DTO.LineChartDTO;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.EntryPI;
import propensi.sinuansa.SINuansa.model.Laporan;
import propensi.sinuansa.SINuansa.model.Transaksi;
import propensi.sinuansa.SINuansa.repository.InventoryDb;
import propensi.sinuansa.SINuansa.repository.LaporanDb;
import propensi.sinuansa.SINuansa.repository.PesananInventoryDb;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LaporanServiceImpl implements LaporanService{
    @Autowired
    LaporanDb laporanDb;

    @Autowired
    InventoryDb inventoryDb;

    @Autowired
    PesananInventoryDb pesananInventoryDb;

    @Override
    public Laporan findLaporanId(Long id){
        Optional<Laporan> laporan = laporanDb.findById(id);
        if(laporan.isPresent()){
            return laporan.get();
        }else return null;
    }

    @Override
    public Laporan getLaporan(int bulan, int tahun, Cabang cabang){
        List<Laporan> laporanList = laporanDb.findAll();
        boolean unexist = true;
        for(Laporan laporan : laporanList){
            if(laporan.getWaktuLaporan().getMonthValue() == bulan && laporan.getWaktuLaporan().getYear() == tahun){
                unexist = false;
                return laporan;
            }
        }
        if(unexist){
            Laporan laporanBaru = new Laporan();
            LocalDateTime tanggal = LocalDateTime.of(tahun, bulan, 1, 0, 0);
            laporanBaru.setWaktuLaporan(tanggal);
            laporanBaru.setCabang(cabang);
            laporanDb.save(laporanBaru);
            return laporanBaru;
        }
        return null;
    }

    @Override
    public LaporanCollectionDTO getLaporanOrganized(Laporan laporan, String bulan, String tahun){
        List<Integer> sizeList = new ArrayList<Integer>(Arrays.asList(3, 2, 7, 3));
        List<String> catList = new ArrayList<String>(Arrays.asList("Pendapatan dari Penjualan", "Harga Pokok Penjualan",
                "Biaya Operasional", "Biaya Lainnya"));
        ArrayList<String> ppList = new ArrayList<String>(Arrays.asList("4-40000 Pendapatan Makanan",
                "4-40100 Diskon Penjualan", "4-40200 Retur Penjualan"));
        ArrayList<String> hppList = new ArrayList<String>(Arrays.asList("5-50000 Beban Pokok Pendapatan Makanan",
                "5-50500 Biaya Produksi"));
        ArrayList<String> boList = new ArrayList<String>(Arrays.asList("6-60000 Biaya Penjualan",
                "6-60008 Biaya Kemitraan", "6-60100 Biaya Umum Dan Administrasi", "6-60101 Gaji",
                "6-60217 Listrik", "6-60400 Biaya Sewa Bangunan", "6-60225 Biaya Internet"));
        ArrayList<String> blList = new ArrayList<String>(Arrays.asList("8-80003 Biaya Lain-Lain",
                "8-80004 Biaya Depresiasi", "8-80999 Beban Lain-Lain"));
        List<ArrayList<String>> refList = new ArrayList<ArrayList<String>>(Arrays.asList(ppList, hppList, boList, blList));
        LaporanCollectionDTO laporanList = new LaporanCollectionDTO();
        laporanList.setCabang(laporan.getCabang().getNama());
        laporanList.setTahun(tahun);
        laporanList.setBulan(bulan);
        for(int i =0; i< sizeList.size(); i++){
            for(int j =0; j< sizeList.get(i); j++){
                LaporanDTO transaksiList = new LaporanDTO(refList.get(i).get(j), catList.get(i), 0L);
                laporanList.getListTransaksi().add(transaksiList);
            }
        }
        for(Transaksi transaksi:laporan.getTransaksiList()){
            for(int r =0; r<laporanList.getListTransaksi().size(); r++){
                if(transaksi.getRefCode().equals(laporanList.getListTransaksi().get(r).getRef())){
                    laporanList.getListTransaksi().get(r).setTotal(laporanList.getListTransaksi().get(r).getTotal()+transaksi.getNominal());
                }
            }
        }
        return laporanList;
    }

    @Override
    public LaporanCollectionDTO calculateReport(LaporanCollectionDTO laporan){
        Long totalPendapatanPenjualan = 0L;
        Long totalHargaPokokPenjualan = 0L;
        Long labaKotor = 0L;
        Long totalBiaya = 0L;
        Long pendapatanBersihOperasional = 0L;
        Long totalBiayaLainnya = 0L;
        Long pendapatanBersih = 0L;
        for(LaporanDTO transaksi : laporan.getListTransaksi()){
            if(transaksi.getRef().equals("4-40000 Pendapatan Makanan"))
                totalPendapatanPenjualan+=transaksi.getTotal();
            else if(transaksi.getRef().equals("4-40100 Diskon Penjualan"))
                totalPendapatanPenjualan-= transaksi.getTotal();
            else if(transaksi.getRef().equals("4-40200 Retur Penjualan"))
                totalPendapatanPenjualan-=transaksi.getTotal();
            else if(transaksi.getKategori().equals("Harga Pokok Penjualan"))
                totalHargaPokokPenjualan+=transaksi.getTotal();
            else if(transaksi.getKategori().equals("Biaya Operasional"))
                totalBiaya+= transaksi.getTotal();
            else{
                totalBiayaLainnya+= transaksi.getTotal();
            }
        }
        labaKotor = totalPendapatanPenjualan - totalHargaPokokPenjualan;
        pendapatanBersihOperasional = labaKotor - totalBiaya;
        pendapatanBersih = pendapatanBersihOperasional - totalBiayaLainnya;

        laporan.setTotalPendapatanPenjualan(totalPendapatanPenjualan);
        laporan.setTotalHargaPokokPenjualan(totalHargaPokokPenjualan);
        laporan.setLabaKotor(labaKotor);
        laporan.setTotalBiaya(totalBiaya);
        laporan.setPendapatanBersihOperasional(pendapatanBersihOperasional);
        laporan.setTotalBiayaLainnya(totalBiayaLainnya);
        laporan.setPendapatanBersih(pendapatanBersih);

        return laporan;
    }

    @Override
    public LineChartDTO getLineChart(int bulan, int tahun, Laporan laporan){
        LineChartDTO lineChart = new LineChartDTO();
        LocalDate date = LocalDate.of(tahun, bulan, 1);
        int days = date.lengthOfMonth();
        ArrayList<Integer> dayList = new ArrayList<>();
        ArrayList<Long> incomeList = new ArrayList<>();
        for(int i = 0; i<days; i++){
            dayList.add(i+1);
            incomeList.add(i, 0L);
            for(int j = 0; j< laporan.getTransaksiList().size(); j++){
                if(laporan.getTransaksiList().get(j).getWaktuTransaksi().getDayOfMonth()==i+1) {
                    if (laporan.getTransaksiList().get(j).getRefCode().equals("4-40000 Pendapatan Makanan")) {
                        incomeList.set(i, incomeList.get(i) + laporan.getTransaksiList().get(j).getNominal());
                    } else {
                        incomeList.set(i, incomeList.get(i) - laporan.getTransaksiList().get(j).getNominal());
                    }
                }
            }
        }
        lineChart.setLabels(dayList);
        lineChart.setData(incomeList);
        return lineChart;
    }

    @Override
    public BarChartDTO getBarChart(int bulan, int tahun){
        BarChartDTO barChart = new BarChartDTO();
        ArrayList<String> kopiList = new ArrayList<>();
        ArrayList<Long> jumlahList = new ArrayList<>();
        for(int i=0; i<inventoryDb.findAll().size(); i++){
            if(inventoryDb.findAll().get(i).isKopi()){
                kopiList.add(inventoryDb.findAll().get(i).getNama());
                jumlahList.add(0L);
            }
        }
        for(int j=0; j<pesananInventoryDb.findAll().size(); j++){
            if(pesananInventoryDb.findAll().get(j).isKopi()
                    && (pesananInventoryDb.findAll().get(j).getWaktuPemesanan().getYear() == tahun
                    && pesananInventoryDb.findAll().get(j).getWaktuPemesanan().getMonthValue() == bulan)){
                for(EntryPI entri: pesananInventoryDb.findAll().get(j).getEntryPIList()){
                    int index = kopiList.indexOf(entri.getInventory().getNama());
                    jumlahList.set(index, jumlahList.get(index)+entri.getKuantitas());
                }
            }
        }
        barChart.setData(jumlahList);
        barChart.setLabels(kopiList);
        return barChart;
    }
}
