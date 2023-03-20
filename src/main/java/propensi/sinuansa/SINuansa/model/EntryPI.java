package propensi.sinuansa.SINuansa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entry_pi")
public class EntryPI implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable=false)
    private String nama;

    @NotNull
    @Column(name = "kuantitas", nullable=false)
    private Long kuantitas;

    @NotNull
    @Column(name = "harga", nullable=false)
    private String harga;

    @ManyToOne
    @JoinColumn(name="id_inventory")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name="id_supplier")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name="id_pesanan_inventory")
    private PesananInventory pesananInventory;
}
