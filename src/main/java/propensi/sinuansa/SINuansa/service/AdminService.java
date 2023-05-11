package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.Admin;

public interface AdminService {
    Admin findAdminId(Long Id);
    void addAdmin(Admin admin);
    Admin updateCabang(Admin admin);
}
