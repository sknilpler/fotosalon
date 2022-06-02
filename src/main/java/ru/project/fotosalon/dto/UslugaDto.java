package ru.project.fotosalon.dto;

public class UslugaDto {
    private Long id;
    private String name;
    private double price;
    private int duration; //hours
    private int numbers;
    private Long id_sotr;

    public UslugaDto(Long id, String name, double price, int duration, int numbers, Long id_sotr) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.id_sotr = id_sotr;
    }

    public UslugaDto() {
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

    public Long getId_sotr() {
        return id_sotr;
    }

    public void setId_sotr(Long id_sotr) {
        this.id_sotr = id_sotr;
    }

    @Override
    public String toString() {
        return "UslugaDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", numbers=" + numbers +
                ", id_sotr=" + id_sotr +
                '}';
    }
}
