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
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable=false)
    private String nama;

    @NotNull
    @Column(name = "isKopi", nullable=false)
    private boolean isKopi;

    @NotNull
    @Column(name = "jumlah", nullable = false)
    private Integer jumlah;

    @NotNull
    @Column(name = "kategori", nullable = false)
    private String kategori;

    @ManyToOne
    @JoinColumn(name="id_cabang")
    private Cabang cabang;

    @OneToMany(mappedBy="inventory")
    private List<Supplier> supplierList;

    @OneToMany(mappedBy="inventory")
    private List<Resep> resepList;

    @OneToMany(mappedBy="inventory")
    private List<EntryPI> entryPIList;
}
