package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Transaksi;

public interface TransaksiService {
    Transaksi findTransactionId(Long Id);
    void saveTransaksi(Transaksi transaksi);
}
