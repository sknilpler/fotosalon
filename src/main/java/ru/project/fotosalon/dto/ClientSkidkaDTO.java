package ru.project.fotosalon.dto;

public class ClientSkidkaDTO {
    private String fio;
    private String usluga;
    private String data;
    private int skidka;
    private double total;
    private String sotrudnik;

    public ClientSkidkaDTO() {

    }

    public ClientSkidkaDTO(String fio, String usluga, String data, int skidka, double total, String sotrudnik) {
        this.fio = fio;
        this.usluga = usluga;
        this.data = data;
        this.skidka = skidka;
        this.total = total;
        this.sotrudnik = sotrudnik;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getUsluga() {
        return usluga;
    }

    public void setUsluga(String usluga) {
        this.usluga = usluga;
    }

    public int getSkidka() {
        return skidka;
    }

    public void setSkidka(int skidka) {
        this.skidka = skidka;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSotrudnik() {
        return sotrudnik;
    }

    public void setSotrudnik(String sotrudnik) {
        this.sotrudnik = sotrudnik;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClientSkidkaDTO{" +
                "fio='" + fio + '\'' +
                ", usluga='" + usluga + '\'' +
                ", skidka=" + skidka +
                ", total=" + total +
                ", sotrudnik='" + sotrudnik + '\'' +
                '}';
    }
}
