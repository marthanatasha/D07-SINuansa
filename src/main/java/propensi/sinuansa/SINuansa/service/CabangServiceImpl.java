package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.repository.CabangDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CabangServiceImpl implements CabangService{
    @Autowired
    CabangDb cabangDb;

    @Override
    public Cabang findCabangId(Long id){
        Optional<Cabang> cabang = cabangDb.findById(id);
        if(cabang.isPresent()){
            return cabang.get();
        }else return null;
    }

    @Override
    public void addCabang (Cabang cb){
        cabangDb.save(cb);
    }

    @Override
    public List<Cabang> getListCabang() {
        return cabangDb.findAll();
    }
}
