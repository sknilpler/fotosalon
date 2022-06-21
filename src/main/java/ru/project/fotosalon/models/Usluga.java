package ru.project.fotosalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    private int skidka;
    private String basisToSkidka;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usluga_id")
    List<UslugaSotrudnik> uslugaSotrudniks;

    private String file;
    private String type;

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

    public Usluga(String name, double price, int duration, int numbers) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
    }

    public Usluga(Long id, String name, double price, int duration, int numbers) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
    }


    public Usluga(Long id, String name, double price, int duration, int numbers, int skidka, String basisToSkidka, String file) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.skidka = skidka;
        this.basisToSkidka = basisToSkidka;
        this.file = file;
    }

    public Usluga(String name, double price, int duration, int numbers, String file) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.file = file;
    }

    public Usluga(String name, double price, int duration, int numbers, int skidka, String basisToSkidka, String file, String type) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.skidka = skidka;
        this.basisToSkidka = basisToSkidka;
        this.file = file;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Usluga{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", numbers=" + numbers +
                '}';
    }
}
