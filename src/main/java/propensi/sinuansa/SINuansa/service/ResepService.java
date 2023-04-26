package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Resep;

import java.util.List;

public interface ResepService {
    Resep findResepId(Long Id);
    List<Resep> getListResep();
    void deleteResep (Resep resep);

}
