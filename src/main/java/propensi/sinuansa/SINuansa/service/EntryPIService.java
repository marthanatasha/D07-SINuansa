package propensi.sinuansa.SINuansa.service;

import propensi.sinuansa.SINuansa.model.EntryPI;
import java.util.List;

public interface EntryPIService {
    EntryPI findEntryPIId(Long Id);
    List<EntryPI> getListEntryPI();
    EntryPI addEntryPI(EntryPI entryPI);

}
