package ru.project.fotosalon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Skidka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String basis;
    private double size;

    @ManyToOne
    Usluga usluga;

    @ManyToOne
    Sotrudnik sotrudnik;

    @ManyToOne
    Client client;

    public Skidka(String basis, double size, Usluga usluga, Sotrudnik sotrudnik, Client client) {
        this.basis = basis;
        this.size = size;
        this.usluga = usluga;
        this.sotrudnik = sotrudnik;
        this.client = client;
    }

    public Skidka(Long id, String basis, double size, Usluga usluga, Sotrudnik sotrudnik, Client client) {
        this.id = id;
        this.basis = basis;
        this.size = size;
        this.usluga = usluga;
        this.sotrudnik = sotrudnik;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Skidka{" +
                "id=" + id +
                ", basis='" + basis + '\'' +
                ", size=" + size +
                ", usluga=" + usluga +
                ", sotrudnik=" + sotrudnik +
                ", client=" + client +
                '}';
    }
}
