package ru.project.fotosalon.dto;

import java.util.Date;

public class ClientStatisticDto {
    private Long id;
    private String fio;
    private int num;
    private Date lastZakazDate;

    public ClientStatisticDto() {
    }

    public ClientStatisticDto(Long id, String fio, int num, Date lastZakazDate) {
        this.id = id;
        this.fio = fio;
        this.num = num;
        this.lastZakazDate = lastZakazDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getLastZakazDate() {
        return lastZakazDate;
    }

    public void setLastZakazDate(Date lastZakazDate) {
        this.lastZakazDate = lastZakazDate;
    }

    @Override
    public String toString() {
        return "ClientStatisticDto{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", num=" + num +
                ", lastZakazDate=" + lastZakazDate +
                '}';
    }
}
