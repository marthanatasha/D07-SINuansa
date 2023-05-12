package propensi.sinuansa.SINuansa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pesanan_customer")
public class PesananCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "harga", nullable=false)
    private Long harga;

    @NotNull
    @Column(name = "diskon", nullable=false)
    private Long diskon;

    @NotNull
    @Column(name = "waktu", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktu;

    @ManyToOne
    @JoinColumn(name="id_cabang")
    private Cabang cabang;

    @OneToMany(mappedBy="pesananCustomer")
    private List<MenuPesanan> menuPesananList;

    @OneToOne(mappedBy = "pesananCustomer")
    private Pembayaran pembayaran;
}
