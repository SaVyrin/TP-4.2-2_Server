package sc.vsu.ru.server.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "Person")
public class PersonEntity {
    @Id
    private Integer personalAccount;
    @Column(name = "password", length = 64)
    private String password;
    @Column(name = "name", length = 64)
    private String name;
    @Column(name = "surname", length = 64)
    private String surname;
    @Column(name = "address", length = 128)
    private String address;
    @OneToMany(mappedBy="id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<IpuEntity> ipus;
}
