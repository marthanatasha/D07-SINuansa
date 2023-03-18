package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Manajer;
import propensi.sinuansa.SINuansa.repository.ManajerDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ManajerServiceImpl implements ManajerService{
    @Autowired
    ManajerDb manajerDb;

    @Override
    public Manajer findManajerId(Long id){
        Optional<Manajer> manajer = manajerDb.findById(id);
        if(manajer.isPresent()){
            return manajer.get();
        }else return null;
    }
}
