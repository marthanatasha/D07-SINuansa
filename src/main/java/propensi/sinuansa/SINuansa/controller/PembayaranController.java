package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.sinuansa.SINuansa.model.Pembayaran;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.service.PembayaranService;
import propensi.sinuansa.SINuansa.service.PesananCustomerService;
import propensi.sinuansa.SINuansa.service.TransaksiService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/payment")
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private PesananCustomerService pesananCustomerService;

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping("/success/{pesanan}/{method}/{source}")
    public String postPayment(@PathVariable Long pesanan, @PathVariable String method, @PathVariable String source,Model model){
        PesananCustomer pemesanan = pesananCustomerService.findPesananCustomerId(pesanan);
        Pembayaran pembayaran = new Pembayaran();
        pembayaran.setHarga(pemesanan.getHarga());

        boolean metbool;
        if(method.equals("Tunai")) metbool = true;
        else metbool = false;

        pembayaran.setId("INV-014/A/20230318");
        pembayaran.setMetode(metbool);
        pembayaran.setSumber(source);
        pembayaran.setPesananCustomer(pemesanan);
        pembayaran.setWaktuBayar(LocalDateTime.now());
        pembayaranService.savePembayaran(pembayaran);

//        Transaksi transaksi = new Transaksi();
//        transaksi.setAkun("Debit");
//        transaksi.setKategori("Order");
//        transaksi.setKuantitas((long)1);
//        transaksi.setPembayaran(pembayaran);
//        transaksi.setNama(pembayaran.getId());
//        transaksi.setWaktuTransaksi(LocalDateTime.now());
//        transaksi.setNominal(pembayaran.getPesananCustomer().getHarga());
//        transaksi.setRefCode("1100");
//        transaksiService.saveTransaksi(transaksi);

        model.addAttribute("pembayaran", pembayaran);
        model.addAttribute("pemesanan", pemesanan);
        return "/invoice/view-invoice";
    }
}