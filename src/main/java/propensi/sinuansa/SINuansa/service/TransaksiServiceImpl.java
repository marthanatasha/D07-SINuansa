package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Transaksi;
import propensi.sinuansa.SINuansa.repository.TransaksiDb;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TransaksiServiceImpl implements TransaksiService{
    @Autowired
    TransaksiDb transaksiDb;

    @Override
    public Transaksi findTransactionId(Long id){
        Optional<Transaksi> admin = transaksiDb.findById(id);
        if(admin.isPresent()){
            return admin.get();
        }else return null;
    }
<<<<<<< HEAD

    @Override
    public void saveTransaksi(Transaksi transaksi){
        transaksiDb.save(transaksi);
    }
=======
>>>>>>> 60bb45b95c425e1f531b20c3534d377c8c0a1f43
}
