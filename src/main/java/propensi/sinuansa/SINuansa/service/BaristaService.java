package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Barista;

public interface BaristaService {
    Barista findBaristaId(Long Id);
    void addBarista (Barista barista);

}
