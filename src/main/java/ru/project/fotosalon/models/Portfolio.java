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
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 3000)
    private String about;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sotrudnik_id",referencedColumnName = "id")
    Sotrudnik sotrudnik;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id")
    private List<PhotoFile> files;

    public Portfolio(String about, Sotrudnik sotrudnik) {
        this.about = about;
        this.sotrudnik = sotrudnik;
    }

    public Portfolio(Long id, String about, Sotrudnik sotrudnik) {
        this.id = id;
        this.about = about;
        this.sotrudnik = sotrudnik;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
                ", about='" + about + '\'' +
                ", sotrudnik=" + sotrudnik +
                '}';
    }
}
