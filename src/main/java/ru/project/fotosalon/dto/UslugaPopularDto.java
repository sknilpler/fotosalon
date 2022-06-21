package ru.project.fotosalon.dto;

public class UslugaPopularDto {
    private Long id;

    private String name;
    private double price;
    private String file;
    private String type;
    private int num;

    public UslugaPopularDto() {
    }

    public UslugaPopularDto(Long id, String name, double price, String file, String type, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.file = file;
        this.type = type;
        this.num = num;
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


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "UslugaPopularDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", file='" + file + '\'' +
                ", type='" + type + '\'' +
                ", num=" + num +
                '}';
    }
}
