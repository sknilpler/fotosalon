package ru.project.fotosalon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.project.fotosalon.utils.ZakazStatus;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Zakaz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date orderDate;
    private Date completeDate;
    private Date issueDate;
    @Enumerated(EnumType.STRING)
    private ZakazStatus status;
    private double totalPrice;
    private int number;

    @ManyToOne
    Client client;

    @ManyToOne
    Sotrudnik sotrudnik;

    @ManyToOne
    Usluga usluga;

    public Zakaz(Date orderDate, Date completeDate, Date issueDate, ZakazStatus status, double totalPrice, Client client, Sotrudnik sotrudnik, Usluga usluga, int number) {
        this.orderDate = orderDate;
        this.completeDate = completeDate;
        this.issueDate = issueDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.client = client;
        this.sotrudnik = sotrudnik;
        this.usluga = usluga;
        this.number = number;
    }

    public Zakaz(Long id, Date orderDate, Date completeDate, Date issueDate, ZakazStatus status, double totalPrice, Client client, Sotrudnik sotrudnik, Usluga usluga, int number) {
        this.id = id;
        this.orderDate = orderDate;
        this.completeDate = completeDate;
        this.issueDate = issueDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.client = client;
        this.sotrudnik = sotrudnik;
        this.usluga = usluga;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Zakaz{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", completeDate=" + completeDate +
                ", issueDate=" + issueDate +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", client=" + client +
                ", sotrudnik=" + sotrudnik +
                ", usluga=" + usluga +
                '}';
    }
}
