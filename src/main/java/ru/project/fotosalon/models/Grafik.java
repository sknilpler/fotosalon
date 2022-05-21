package ru.project.fotosalon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.project.fotosalon.utils.TypeHour;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Grafik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date data;
    @Enumerated(EnumType.STRING)
    private TypeHour type;

    @ManyToOne
    Sotrudnik sotrudnik;

    public Grafik(Date data, Sotrudnik sotrudnik) {
        this.data = data;
        this.type = TypeHour.FREE;
        this.sotrudnik = sotrudnik;
    }

    public Grafik(Long id, Date data, TypeHour type, Sotrudnik sotrudnik) {
        this.id = id;
        this.data = data;
        this.type = type;
        this.sotrudnik = sotrudnik;
    }

    @Override
    public String toString() {
        return "Grafik{" +
                "id=" + id +
                ", data=" + data +
                ", type='" + type + '\'' +
                ", sotrudnik=" + sotrudnik +
                '}';
    }
}
