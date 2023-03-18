package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Resep;
import propensi.sinuansa.SINuansa.repository.ResepDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ResepServiceImpl implements ResepService{
    @Autowired
    ResepDb resepDb;

    @Override
    public Resep findResepId(Long id){
        Optional<Resep> resep = resepDb.findById(id);
        if(resep.isPresent()){
            return resep.get();
        }else return null;
    }
}
