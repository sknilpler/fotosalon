package ru.project.fotosalon.dto;

public class StatSklad2Dto {

    private String name;
    private String type;
    private String units;
    private double total;
    private String date;

    public StatSklad2Dto() {
    }

    public StatSklad2Dto(String name, String type, String units, double total, String date) {
        this.name = name;
        this.type = type;
        this.units = units;
        this.total = total;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StatSklad2Dto{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", total=" + total +
                ", date='" + date + '\'' +
                '}';
    }
}