package propensi.sinuansa.SINuansa.service;
import propensi.sinuansa.SINuansa.model.PesananCustomer;
import java.util.List;

public interface PesananCustomerService {
    void addPesananCustomer (PesananCustomer pesananCustomer);
    PesananCustomer getPesananCustomerById (Long id);

}