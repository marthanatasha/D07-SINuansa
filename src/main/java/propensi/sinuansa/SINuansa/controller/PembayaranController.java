package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import propensi.sinuansa.SINuansa.DTO.InvoiceDTO;
import propensi.sinuansa.SINuansa.DTO.ItemDTO;
import propensi.sinuansa.SINuansa.model.MenuPesanan;
import propensi.sinuansa.SINuansa.model.Pembayaran;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import propensi.sinuansa.SINuansa.model.Transaksi;
import propensi.sinuansa.SINuansa.service.PembayaranService;
import propensi.sinuansa.SINuansa.service.PesananCustomerService;
import propensi.sinuansa.SINuansa.service.TransaksiService;
import propensi.sinuansa.SINuansa.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private PesananCustomerService pesananCustomerService;

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private UserService userService;

    @RequestMapping("/success/{method}/{source}/{pesanan}")
    public String postPayment(@PathVariable Long pesanan,
                              @PathVariable String method,
                              @PathVariable String source,
                              @RequestParam(value="inputCash", required = false) Long inputCash,
                              Model model,
                              Map<String, Object> modell,
                              Authentication authentication){
        PesananCustomer pemesanan = pesananCustomerService.findPesananCustomerId(pesanan);
        Pembayaran pembayaran = new Pembayaran();
        pembayaran.setHarga(pemesanan.getHarga());

        boolean metbool;
        if(method.equalsIgnoreCase("Tunai")) metbool = true;
        else metbool = false;
        //todo
        String id = "INV-" + pemesanan.getId() + "/" + pemesanan.getCabang().getNama() +"/" + LocalDateTime.now().getYear();

        //bulan
        if(LocalDateTime.now().getMonthValue()<10)id+="0"+LocalDateTime.now().getMonthValue();
        else id+= LocalDateTime.now().getMonthValue();
        //tanggal
        if(LocalDateTime.now().getDayOfMonth()<10) id+="0" + LocalDateTime.now().getDayOfMonth();
        else id+=LocalDateTime.now().getDayOfMonth();

        pembayaran.setId(id);
        pembayaran.setMetode(metbool);
        pembayaran.setSumber(source);
        pembayaran.setPesananCustomer(pemesanan);
        pembayaran.setWaktuBayar(LocalDateTime.now());
        pembayaranService.savePembayaran(pembayaran);

        Transaksi transaksi = new Transaksi();
        transaksi.setAkun("Debit");
        transaksi.setKategori("Pendapatan Penjualan");
        transaksi.setKuantitas((long)1);
        transaksi.setPembayaran(pembayaran);
        transaksi.setNama(pembayaran.getId());
        transaksi.setWaktuTransaksi(LocalDateTime.now());
        transaksi.setNominal(pembayaran.getPesananCustomer().getHarga());
        transaksi.setRefCode("4-40000 Pendapatan Makanan");
        transaksi.setCabang(userService.findByUsername(authentication.getName()).getCabang());
        transaksiService.saveTransaksi(transaksi);
        pembayaran.setTransaksi(transaksi);
        pembayaranService.savePembayaran(pembayaran);

        boolean isDiskon = true;
        if (pemesanan.getDiskon() == 0) {
            isDiskon = false;
        }

        if (isDiskon) {
            Transaksi transaksiDiskon = new Transaksi();
            transaksiDiskon.setAkun("Credit");
            transaksiDiskon.setKategori("Pendapatan Penjualan");
            transaksiDiskon.setKuantitas((long)1);
            transaksiDiskon.setNama(transaksi.getNama());
            transaksiDiskon.setWaktuTransaksi(LocalDateTime.now());
            transaksiDiskon.setNominal(pemesanan.getDiskon());
            transaksiDiskon.setRefCode("4-40100 Diskon Penjualan");
            transaksiDiskon.setCabang(userService.findByUsername(authentication.getName()).getCabang());
            transaksiService.saveTransaksi(transaksiDiskon);
        }

        List<ItemDTO> listItem = new ArrayList<>();

        for(MenuPesanan item : pemesanan.getMenuPesananList()){
            ItemDTO itemDTO = new ItemDTO(item.getMenu().getNama(), item.getJumlah(), item.getMenu().getHarga(), item.getJumlah()*item.getMenu().getHarga());
            listItem.add(itemDTO);
        }
        String namaCab = pemesanan.getCabang().getNama();
        String alamat = pemesanan.getCabang().getAlamat();
        String noTelp = "0"+pemesanan.getCabang().getNoTelp();
        InvoiceDTO invoiceDTO = new InvoiceDTO(namaCab, alamat, noTelp, pembayaran.getWaktuBayar(), pembayaran.getId(), listItem, source, pemesanan.getHarga());

        model.addAttribute("metbool", metbool);

        if (metbool) {
            model.addAttribute("inputCash", inputCash);
            model.addAttribute("kembalian", inputCash - pemesanan.getHarga() - pemesanan.getDiskon());
        }

        model.addAttribute("totalHarga", pemesanan.getHarga() - pemesanan.getDiskon());
        model.addAttribute("isDiskon", isDiskon);
        model.addAttribute("diskon", pemesanan.getDiskon());
        model.addAttribute("invoice", invoiceDTO);
        return "invoice/view-invoice";
    }

    @GetMapping("/option/{custId}")
    public String optionPayment(@PathVariable Long custId, Model model) {
        PesananCustomer pCust = pesananCustomerService.findPesananCustomerId(custId);
        model.addAttribute("pesananCustomer", pCust);
        model.addAttribute("custId", pCust.getId());
        return "Payment/option";
    }

    //todo: generate QRIS (nontunai) dan kembalian Tunai (form input trus return hasil kembaliannya berapa)
    @GetMapping("/nontunai/{source}/{custId}")
    public String generateQRIS(@PathVariable Long custId,
                               @PathVariable String source,
                               Model model) {
        PesananCustomer pc = pesananCustomerService.findPesananCustomerId(custId);
        Long total_harga = pc.getHarga() - pc.getDiskon();
        model.addAttribute("pesananCustomer", pc);
        model.addAttribute("totalHarga", total_harga);
        model.addAttribute("source", source.toUpperCase());
        return "Payment/nontunai";
    }

    @GetMapping("/tunai/{custId}")
    public String inputTunai(@PathVariable Long custId,
                             Model model) {
        PesananCustomer pc = pesananCustomerService.findPesananCustomerId(custId);
        Long inputCash = 0L;
        Long total_harga = pc.getHarga() - pc.getDiskon();
        model.addAttribute("pesananCustomer", pc);
        model.addAttribute("totalHarga", total_harga);
        model.addAttribute("inputCash", inputCash);
        //todo: pakai ajax kah untuk retrieve function ngitung kembalian? alt: javascript

        return "Payment/tunai";
    }
}