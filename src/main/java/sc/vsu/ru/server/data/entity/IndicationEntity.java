package sc.vsu.ru.server.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Indication")
public class IndicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ipu_id", nullable=false)
    private IpuEntity ipu;
    @Column(name = "value")
    private int value;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    @Column(name = "payment_value")
    private int paymentValue;
    @Column(name = "paid")
    private boolean paid;
}
