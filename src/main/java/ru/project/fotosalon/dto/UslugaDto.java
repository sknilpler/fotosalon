package ru.project.fotosalon.dto;

import ru.project.fotosalon.models.Sotrudnik;

import java.util.List;

public class UslugaDto {
    private Long id;
    private String name;
    private double price;
    private int duration; //hours
    private int numbers;
    private String file;
    private String type;

    public UslugaDto(Long id, String name, double price, int duration, int numbers, String file) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.file = file;
    }

    public UslugaDto(Long id, String name, double price, int duration, int numbers, String file, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.file = file;
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public UslugaDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }


    @Override
    public String toString() {
        return "UslugaDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", numbers=" + numbers +
                ", file='" + file + '\'' +
                '}';
    }
}
