package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.PesananInventory;

import java.util.List;

public interface PesananInventoryService {
    PesananInventory findPesananInventoryId(Long Id);
    public PesananInventory addPesananInventory(PesananInventory pesananInventory);
    public List<PesananInventory> getListPesananInventory();
}
