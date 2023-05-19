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
@Table(name = "transaksi")
public class Transaksi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nama", nullable =false)
    private String nama;

    @NotNull
    @Column(name = "akun", nullable =false)
    private String akun;

    @NotNull
    @Column(name = "ref_code", nullable =false)
    private String refCode;

    @NotNull
    @Column(name = "kategori", nullable =false)
    private String kategori;

    @NotNull
    @Column(name = "nominal", nullable =false)
    private Long nominal;

    @NotNull
    @Column(name = "kuantitas", nullable =false)
    private Long kuantitas;

    @NotNull
    @Column(name = "waktu_transaksi", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuTransaksi;

    @OneToOne(mappedBy = "transaksi")
    private Pembayaran pembayaran;

    @OneToOne(mappedBy = "transaksi")
    private PesananInventory pesananInventory;

    @ManyToOne
    @JoinColumn(name="id_laporan")
    private Laporan laporan;

    @ManyToOne
    @JoinColumn(name="id_cabang")
    private Cabang cabang;
}
