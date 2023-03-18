package propensi.sinuansa.SINuansa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resep")
public class Resep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jumlah", nullable=false)
    private Long jumlah;

    @ManyToOne
    @JoinColumn(name="id_menu")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name="id_inventory")
    private Inventory inventory;
}
