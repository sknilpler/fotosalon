package ru.project.fotosalon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Rashodnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double numbers;

    @ManyToOne
    Usluga usluga;

    @ManyToOne
    Sklad sklad;

    public Rashodnik(double numbers, Usluga usluga, Sklad sklad) {
        this.numbers = numbers;
        this.usluga = usluga;
        this.sklad = sklad;
    }

    public Rashodnik(Long id, double numbers, Usluga usluga, Sklad sklad) {
        this.id = id;
        this.numbers = numbers;
        this.usluga = usluga;
        this.sklad = sklad;
    }

    @Override
    public String toString() {
        return "Rashodnik{" +
                "id=" + id +
                ", numbers=" + numbers +
                ", usluga=" + usluga +
                ", sklad=" + sklad +
                '}';
    }
}
