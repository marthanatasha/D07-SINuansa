package propensi.sinuansa.SINuansa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_menu", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="id_inventory", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Inventory inventory;
}
