package ru.project.fotosalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Usluga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private double price;
    private int duration; //hours
    private int numbers;

    @ManyToOne
    Sotrudnik sotrudnik;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usluga_id")
    List<Skidka> skidkaList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usluga_id")
    List<Rashodnik> rashodnikList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usluga_id")
    List<Zakaz> zakazList;

    public Usluga(String name, double price, int duration, int numbers, Sotrudnik sotrudnik) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.sotrudnik = sotrudnik;
        this.numbers = numbers;
    }

    public Usluga(Long id, String name, double price, int duration, int numbers) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
    }

    public Usluga(String name, double price, int duration, int numbers) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
    }

    public Usluga(Long id, String name, double price, int duration, int numbers, Sotrudnik sotrudnik) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.sotrudnik = sotrudnik;
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "Usluga{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", numbers=" + numbers +
                ", sotrudnik=" + sotrudnik +
                '}';
    }
}
