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

    private String fio;
    private String email;
    private String phone;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "client_id")
    List<Skidka> skidkaList;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "client_id")
    List<Zakaz> zakazList;

    public Client(String fio, String email, String phone) {
        this.fio = fio;
        this.email = email;
        this.phone = phone;
    }

    public Client(Long id, String fio, String email, String phone) {
        this.id = id;
        this.fio = fio;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
