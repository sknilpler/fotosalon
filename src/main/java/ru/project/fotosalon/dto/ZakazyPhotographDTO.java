package ru.project.fotosalon.dto;

import ru.project.fotosalon.models.Client;
import ru.project.fotosalon.models.Grafik;
import ru.project.fotosalon.models.Sotrudnik;
import ru.project.fotosalon.models.Usluga;
import ru.project.fotosalon.utils.ZakazStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

public class ZakazyPhotographDTO {

    private Long id;
    private Date orderDate;
    private Date completeDate;
    private Date issueDate;
    private ZakazStatus status;
    private double totalPrice;
    private int number;
    private List<Grafik> grafiks;
    Client client;
    Sotrudnik sotrudnik;
    Usluga usluga;

    public ZakazyPhotographDTO(Long id, Date orderDate, Date completeDate, Date issueDate, ZakazStatus status, double totalPrice, int number, List<Grafik> grafiks, Client client, Sotrudnik sotrudnik, Usluga usluga) {
        this.id = id;
        this.orderDate = orderDate;
        this.completeDate = completeDate;
        this.issueDate = issueDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.number = number;
        this.grafiks = grafiks;
        this.client = client;
        this.sotrudnik = sotrudnik;
        this.usluga = usluga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public ZakazStatus getStatus() {
        return status;
    }

    public void setStatus(ZakazStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Grafik> getGrafiks() {
        return grafiks;
    }

    public void setGrafiks(List<Grafik> grafiks) {
        this.grafiks = grafiks;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Sotrudnik getSotrudnik() {
        return sotrudnik;
    }

    public void setSotrudnik(Sotrudnik sotrudnik) {
        this.sotrudnik = sotrudnik;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

    @Override
    public String toString() {
        return "ZakazyPhotographDTO{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", completeDate=" + completeDate +
                ", issueDate=" + issueDate +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", number=" + number +
                ", grafiks=" + grafiks +
                ", client=" + client +
                ", sotrudnik=" + sotrudnik +
                ", usluga=" + usluga +
                '}';
    }
}
