package propensi.sinuansa.SINuansa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.sinuansa.SINuansa.DTO.InvoiceDTO;
import propensi.sinuansa.SINuansa.DTO.ItemDTO;
import propensi.sinuansa.SINuansa.model.*;
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

    @GetMapping("/success/{pesanan}/{method}/{source}")
    public String postPayment(@PathVariable Long pesanan, @PathVariable String method, @PathVariable String source, Model model,
                              Map<String, Object> modell, Authentication authentication){
        PesananCustomer pemesanan = pesananCustomerService.findPesananCustomerId(pesanan);
        Pembayaran pembayaran = new Pembayaran();
        pembayaran.setHarga(pemesanan.getHarga());
        UserModel user = userService.findByUsername(authentication.getName());
        Cabang cabang = user.getCabang();

        boolean metbool;
        if(method.equals("Tunai")) metbool = true;
        else metbool = false;
        String id = "INV-" + pemesanan.getId() + "/" + pemesanan.getCabang().getNama() +"/" + LocalDateTime.now().getYear();
        if(LocalDateTime.now().getMonthValue()<10)id+="0"+LocalDateTime.now().getMonthValue();
        else id+= LocalDateTime.now().getMonthValue();
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
        transaksi.setCabang(cabang);
        transaksiService.saveTransaksi(transaksi);

        List<ItemDTO> listItem = new ArrayList<>();

        for(MenuPesanan item : pemesanan.getMenuPesananList()){
            ItemDTO itemDTO = new ItemDTO(item.getMenu().getNama(), item.getJumlah(), item.getMenu().getHarga(), item.getJumlah()*item.getMenu().getHarga());
            listItem.add(itemDTO);
        }
        String namaCab = pemesanan.getCabang().getNama();
        String alamat = pemesanan.getCabang().getAlamat();
        String noTelp = "0"+pemesanan.getCabang().getNoTelp();
        InvoiceDTO invoiceDTO = new InvoiceDTO(namaCab, alamat, noTelp, pembayaran.getWaktuBayar(), pembayaran.getId(), listItem, source, pemesanan.getHarga());

        model.addAttribute("invoice", invoiceDTO);
        return "/invoice/view-invoice";
    }
}