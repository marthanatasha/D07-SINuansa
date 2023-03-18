package propensi.sinuansa.SINuansa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pembayaran")
public class Pembayaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "metode", nullable=false)
    private boolean metode;

    @Column(name = "sumber")
    private String sumber;

    @NotNull
    @Column(name = "harga", nullable=false)
    private Long harga;

    @NotNull
    @Column(name = "waktu_bayar", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuBayar;

    @OneToOne
    @JoinColumn(name = "pesananCustomer", referencedColumnName = "id")
    private PesananCustomer pesananCustomer;

    @OneToOne
    @JoinColumn(name = "transaksi", referencedColumnName = "id")
    private Transaksi transaksi;
}
