package propensi.sinuansa.SINuansa.service;


import propensi.sinuansa.SINuansa.model.Menu;
import propensi.sinuansa.SINuansa.model.PesananCustomer;

public interface PesananCustomerService {
    PesananCustomer findPesananCustomerId(Long Id);
    void addPesananCustomer(PesananCustomer pesananCustomer);

}

