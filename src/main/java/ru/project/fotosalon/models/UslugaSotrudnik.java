package ru.project.fotosalon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UslugaSotrudnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Usluga usluga;
    @ManyToOne
    private Sotrudnik sotrudnik;

    public UslugaSotrudnik(Usluga usluga, Sotrudnik sotrudnik) {
        this.usluga = usluga;
        this.sotrudnik = sotrudnik;
    }

    @Override
    public String toString() {
        return "UslugaSotrudnik{" +
                "id=" + id +
                ", usluga=" + usluga +
                ", sotrudnik=" + sotrudnik +
                '}';
    }
}
