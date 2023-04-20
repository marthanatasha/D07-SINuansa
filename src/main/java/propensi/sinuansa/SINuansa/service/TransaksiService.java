package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Transaksi;

import java.util.List;

public interface TransaksiService {
    Transaksi findTransactionId(Long Id);
    void saveTransaksi(Transaksi transaksi);
    List<Transaksi> getTransaksiList();
}
