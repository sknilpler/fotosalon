package ru.project.fotosalon.dto;

public class UslugaRenderedDto {
    private Long id;
    private String name;
    private double price;
    private int num;
    private double total;

    public UslugaRenderedDto() {
    }

    public UslugaRenderedDto(Long id, String name, double price, int num, double total) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UslugaRenderedDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", total=" + total +
                '}';
    }
}
