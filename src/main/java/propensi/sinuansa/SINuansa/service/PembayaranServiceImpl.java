package propensi.sinuansa.SINuansa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.sinuansa.SINuansa.model.Pembayaran;
import propensi.sinuansa.SINuansa.repository.PembayaranDb;

import java.util.Optional;

@Service
public class PembayaranServiceImpl implements  PembayaranService{
    @Autowired
    PembayaranDb pembayaranDb;

    @Override
    public Pembayaran findPembayaranId(Long id){
        Optional<Pembayaran> pembayaran = pembayaranDb.findById(id);
        if(pembayaran.isPresent()){
            return pembayaran.get();
        }else return null;
    }
}
