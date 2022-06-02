package ru.project.fotosalon.dto;

import java.util.List;

public class UslugaRashodnikiDto {
    private String name;
    private double price;
    private int duration; //hours
    private int numbers;
    private Long id_sotr;

    private List<RashodnikDto> list;

    public UslugaRashodnikiDto() {
    }

    public UslugaRashodnikiDto(String name, double price, int duration, int numbers, Long id_sotr) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.id_sotr = id_sotr;
    }

    public UslugaRashodnikiDto(String name, double price, int duration, int numbers, Long id_sotr, List<RashodnikDto> list) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.id_sotr = id_sotr;
        this.list = list;
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

    public List<RashodnikDto> getList() {
        return list;
    }

    public void setList(List<RashodnikDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UslugaRashodnikiDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", numbers=" + numbers +
                ", id_sotr=" + id_sotr +
                ", list=" + list +
                '}';
    }
}
