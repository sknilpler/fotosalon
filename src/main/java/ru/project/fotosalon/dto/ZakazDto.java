package ru.project.fotosalon.dto;

import java.util.Date;

public class ZakazDto {

    private Date orderDate;
    private Date completeDate;
    private Date issueDate;
    private String status;
    private double totalPrice;
    private int number;
    private Long id_sotr;
    private Long id_client;
    private Long id_usligi;

    public ZakazDto() {
    }

    public ZakazDto(Date orderDate, Date completeDate, Date issueDate, String status, double totalPrice, int number, Long id_sotr, Long id_client, Long id_usligi) {
        this.orderDate = orderDate;
        this.completeDate = completeDate;
        this.issueDate = issueDate;
        this.status = status;
        this.totalPrice = totalPrice;
        this.number = number;
        this.id_sotr = id_sotr;
        this.id_client = id_client;
        this.id_usligi = id_usligi;
    }

    public ZakazDto(int number, Long id_sotr, Long id_client, Long id_usligi) {
        this.number = number;
        this.id_sotr = id_sotr;
        this.id_client = id_client;
        this.id_usligi = id_usligi;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Long getId_sotr() {
        return id_sotr;
    }

    public void setId_sotr(Long id_sotr) {
        this.id_sotr = id_sotr;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    public Long getId_usligi() {
        return id_usligi;
    }

    public void setId_usligi(Long id_usligi) {
        this.id_usligi = id_usligi;
    }

    @Override
    public String toString() {
        return "ZakazDto{" +
                "orderDate=" + orderDate +
                ", completeDate=" + completeDate +
                ", issueDate=" + issueDate +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", number=" + number +
                ", id_sotr=" + id_sotr +
                ", id_client=" + id_client +
                ", id_usligi=" + id_usligi +
                '}';
    }
}
