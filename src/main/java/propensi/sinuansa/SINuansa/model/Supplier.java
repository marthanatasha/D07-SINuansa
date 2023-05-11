package propensi.sinuansa.SINuansa.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Data
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

    @NotNull
    @Column(name = "material", nullable=false)
    private String material;

    @NotNull
    @Column(name = "status", nullable = false)
    private  String status;

    @ManyToOne
    @JoinColumn(name="cabang")
    private Cabang cabang;

    @ManyToOne
    @JoinColumn(name="id_inventory")
    private Inventory inventory;

    @OneToMany(mappedBy="supplier")
    private List<EntryPI> entryPIList;
}
