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
@Table(name = "menu_pesanan")
public class MenuPesanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable=false)
    private Long jumlah;

    @ManyToOne
    @JoinColumn(name="id_menu")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name="id_pesanan_customer")
    private PesananCustomer pesananCustomer;
}
