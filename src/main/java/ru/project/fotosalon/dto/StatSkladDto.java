package ru.project.fotosalon.dto;

public class StatSkladDto {
    private Long id;
    private String name;
    private String type;
    private String units;
    private int price;
    private int number;

    public StatSkladDto() {
    }

    public StatSkladDto(String name, String type, String units, int price, int number) {
        this.name = name;
        this.type = type;
        this.units = units;
        this.price = price;
        this.number = number;
    }

    public StatSkladDto(Long id, String name, String type, String units, int price, int number) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.units = units;
        this.price = price;
        this.number = number;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "StatSkladDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", price=" + price +
                ", number=" + number +
                '}';
    }
}
