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
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;
    private String fio;
    private String email;
    private String phone;
    private int skidka;
    private String basisToSkidka;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "client_id")
    List<Skidka> skidkaList;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "client_id")
    List<Zakaz> zakazList;

    public Client(Long id, String username, String fio, String email, String phone) {
        this.id = id;
        this.username = username;
        this.fio = fio;
        this.email = email;
        this.phone = phone;
    }

    public Client(String username, String fio, String email, String phone) {
        this.username = username;
        this.fio = fio;
        this.email = email;
        this.phone = phone;
    }

    public Client(String fio, String email, String phone) {
        this.fio = fio;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", skidka=" + skidka +
                '}';
    }
}
