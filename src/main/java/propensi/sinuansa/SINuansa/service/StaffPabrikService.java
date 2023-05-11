package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.StaffPabrik;

public interface StaffPabrikService {
    StaffPabrik findStaffPabrikId(Long Id);
    void addStaff(StaffPabrik staff);
    StaffPabrik update(StaffPabrik staff);
}
