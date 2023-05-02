package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.PesananInventory;

import java.util.List;

public interface PesananPabrikService {
    PesananInventory findPesananInventoryId(Long id);
    public PesananInventory updatePesanan(PesananInventory pesananInventory);
    public List<PesananInventory> getListPesanan();

    public PesananInventory updateStatusPesanan(PesananInventory pesananInventory);
}
