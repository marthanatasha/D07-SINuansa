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
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable=false)
    private String nama;

    @NotNull
    @Column(name = "kontak", nullable=false)
    private String link;

    @ManyToOne
    @JoinColumn(name="id_cabang")
    private Cabang cabang;

    @ManyToOne
    @JoinColumn(name="id_inventory")
    private Inventory inventory;

    @OneToMany(mappedBy="supplier")
    private List<EntryPI> entryPIList;
}
