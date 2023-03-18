package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Barista;
import propensi.sinuansa.SINuansa.repository.BaristaDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BaristaServiceImpl implements BaristaService{
    @Autowired
    BaristaDb baristaDb;

    @Override
    public Barista findBaristaId(Long id){
        Optional<Barista> barista = baristaDb.findById(id);
        if(barista.isPresent()){
            return barista.get();
        }else return null;
    }
}
