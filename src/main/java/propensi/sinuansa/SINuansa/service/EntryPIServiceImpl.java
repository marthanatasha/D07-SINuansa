package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.EntryPI;
import propensi.sinuansa.SINuansa.repository.EntryPIDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EntryPIServiceImpl implements EntryPIService{
    @Autowired
    EntryPIDb entryPIDb;

    @Override
    public EntryPI findEntryPIId(Long id){
        Optional<EntryPI> entryPI = entryPIDb.findById(id);
        if(entryPI.isPresent()){
            return entryPI.get();
        }else return null;
    }

    @Override
    public List<EntryPI> getListEntryPI() {
        return entryPIDb.findAll();
    }

    @Override
    public EntryPI addEntryPI(EntryPI entryPI) {
        return entryPIDb.save(entryPI);
    }
}
