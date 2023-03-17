package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Laporan;
import propensi.sinuansa.SINuansa.repository.LaporanDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LaporanServiceImpl implements LaporanService{
    @Autowired
    LaporanDb laporanDb;

    @Override
    public Laporan findLaporanId(Long id){
        Optional<Laporan> laporan = laporanDb.findById(id);
        if(laporan.isPresent()){
            return laporan.get();
        }else return null;
    }
}
