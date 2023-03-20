package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Cabang;

import java.util.List;

public interface CabangService {
    Cabang findCabangId(Long Id);
    void addCabang (Cabang cb);
    List<Cabang> getListCabang();

}
