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
@Table(name = "pesanan_inventory")
public class PesananInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_cabang")
    private Cabang cabang;

    @NotNull
    @Column(name = "status", nullable=false)
    private String status;

    @NotNull
    @Column(name = "isKopi", nullable=false)
    private boolean isKopi;

    @NotNull
    @Column(name = "waktu_pemesanan", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuPemesanan;

    @NotNull
    @Column(name = "bukti", nullable=false)
    private String bukti;

    @NotNull
    @Column(name = "harga", nullable=false)
    private Long harga;

    @OneToMany(mappedBy="pesananInventory")
    private List<EntryPI> entryPIList;

    @OneToOne
    @JoinColumn(name = "transaksi", referencedColumnName = "id")
    private Transaksi transaksi;
}
