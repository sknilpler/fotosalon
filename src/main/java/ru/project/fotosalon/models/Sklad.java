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
public class Sklad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String type;
    private String units;
    private int number;
    private int price;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sklad_id")
    List<Rashodnik> rashodnikList;

    public Sklad(String name, String type, String units, int number, int price) {
        this.name = name;
        this.type = type;
        this.units = units;
        this.number = number;
        this.price = price;
    }

    public Sklad(Long id, String name, String type, String units, int number, int price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.units = units;
        this.number = number;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Sklad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
