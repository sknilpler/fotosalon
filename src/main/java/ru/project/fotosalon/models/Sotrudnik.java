package ru.project.fotosalon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Sotrudnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String fio;
    private String post;
    private String phone;
    private double oklad;
    private double premiya;

    @Lob
    private byte[] avatar;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sotrudnik_id")
    List<Usluga> uslugaList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sotrudnik_id")
    List<Skidka> skidkaList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sotrudnik_id")
    List<Grafik> grafikList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sotrudnik_id")
    List<Zakaz> zakazList;

    public Sotrudnik(String username, String fio, String post, String phone, double oklad, double premiya) {
        this.username = username;
        this.fio = fio;
        this.post = post;
        this.phone = phone;
        this.oklad = oklad;
        this.premiya = premiya;
    }

    public Sotrudnik(Long id, String username, String fio, String post, String phone, double oklad, double premiya) {
        this.id = id;
        this.username = username;
        this.fio = fio;
        this.post = post;
        this.phone = phone;
        this.oklad = oklad;
        this.premiya = premiya;
    }

    public Sotrudnik(String username, String fio, String post, String phone, double oklad, double premiya, byte[] avatar) {
        this.username = username;
        this.fio = fio;
        this.post = post;
        this.phone = phone;
        this.oklad = oklad;
        this.premiya = premiya;
        this.avatar = avatar;
    }

    public Sotrudnik(Long id, String username, String fio, String post, String phone, double oklad, double premiya, byte[] avatar) {
        this.id = id;
        this.username = username;
        this.fio = fio;
        this.post = post;
        this.phone = phone;
        this.oklad = oklad;
        this.premiya = premiya;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Sotrudnik{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fio='" + fio + '\'' +
                ", post='" + post + '\'' +
                ", phone='" + phone + '\'' +
                ", oklad=" + oklad +
                ", premiya=" + premiya +
                ", avatar=" + Arrays.toString(avatar) +
                '}';
    }
}
