package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Cabang;
import propensi.sinuansa.SINuansa.model.Laporan;
import propensi.sinuansa.SINuansa.model.Transaksi;

import java.util.List;

public interface TransaksiService {
    Transaksi findTransactionId(Long Id);
    void saveTransaksi(Transaksi transaksi);
    List<Transaksi> getTransaksiList();
    void getTransaksiLaporanList(int bulan, int tahun, Laporan laporan, Cabang cabang);
}
