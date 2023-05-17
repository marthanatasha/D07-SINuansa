package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Laporan;
import propensi.sinuansa.SINuansa.model.Transaksi;
import propensi.sinuansa.SINuansa.repository.LaporanDb;
import propensi.sinuansa.SINuansa.repository.TransaksiDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransaksiServiceImpl implements TransaksiService{
    @Autowired
    TransaksiDb transaksiDb;

    @Autowired
    LaporanDb laporanDb;

    @Override
    public Transaksi findTransactionId(Long id){
        Optional<Transaksi> admin = transaksiDb.findById(id);
        if(admin.isPresent()){
            return admin.get();
        }else return null;
    }

    @Override
    public void saveTransaksi(Transaksi transaksi){
        transaksiDb.save(transaksi);
    }

    @Override
    public List<Transaksi> getTransaksiList(){
        return transaksiDb.findAll();
    }

    @Override
    public void getTransaksiLaporanList(int bulan, int tahun, Laporan laporan, Cabang cabang) {
        List<Transaksi> allTransaksi = transaksiDb.findAll();
        List<Transaksi> specTransaksi = new ArrayList<Transaksi>();
        for(Transaksi eachTransaksi : allTransaksi){
            if(eachTransaksi.getWaktuTransaksi().getMonthValue() == bulan
                    && eachTransaksi.getWaktuTransaksi().getYear() == tahun
                    && eachTransaksi.getCabang().getId() == cabang.getId()){
                specTransaksi.add(eachTransaksi);
                eachTransaksi.setLaporan(laporan);
                transaksiDb.save(eachTransaksi);
            }
        }
        laporan.setTransaksiList(specTransaksi);
        laporanDb.save(laporan);
    }
}
