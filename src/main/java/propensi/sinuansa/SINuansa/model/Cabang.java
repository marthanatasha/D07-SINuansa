package propensi.sinuansa.SINuansa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cabang")
public class Cabang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable=false)
    private String nama;

    @OneToMany(mappedBy="cabang")
    private List<User> userList;

    @OneToMany(mappedBy="cabang")
    private List<Supplier> supplierList;

    @OneToMany(mappedBy="cabang")
    private List<Inventory> inventoryList;

    @OneToMany(mappedBy="cabang")
    private List<Menu> menuList;

    @OneToMany(mappedBy="cabang")
    private List<PesananCustomer> pesananCustomerList;

    @OneToMany(mappedBy="cabang")
    private List<Laporan> laporanList;

    @OneToMany(mappedBy="cabang")
    private List<PesananInventory> pesananInventoryList;

}
