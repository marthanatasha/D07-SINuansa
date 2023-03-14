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
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable=false)
    private String nama;

    @NotNull
    @Column(name = "harga", nullable=false)
    private Long harga;

    @NotNull
    @Column(name = "kategori", nullable=false)
    private String kategori;

    @ManyToOne
    @JoinColumn(name="id_cabang")
    private Cabang cabang;

    @OneToMany(mappedBy="menu")
    private List<Resep> resepList;

    @OneToMany(mappedBy="menu")
    private List<MenuPesanan> menuPesananList;
}
