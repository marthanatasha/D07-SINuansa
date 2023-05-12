package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Manajer;

public interface ManajerService {
    Manajer findManajerId(Long Id);
    void addManajer(Manajer manajer);
    Manajer update(Manajer manajer);

}
