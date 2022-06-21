package ru.project.fotosalon.dto;

import ru.project.fotosalon.models.Rashodnik;
import ru.project.fotosalon.models.Sklad;
import ru.project.fotosalon.models.Sotrudnik;

import java.util.List;
import java.util.Set;

public class UslugaRashodnikiDto2 {

    private String name;
    private double price;
    private int duration; //hours
    private int numbers;
    private List<Sotrudnik> sotr;

    private List<Sklad> rashodniki;

    public UslugaRashodnikiDto2() {
    }

    public UslugaRashodnikiDto2(String name, double price, int duration, int numbers, List<Sklad> rashodniki, List<Sotrudnik> sotr) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.numbers = numbers;
        this.sotr = sotr;
        this.rashodniki = rashodniki;
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

    public List<Sotrudnik> getSotr() {
        return sotr;
    }

    public void setSotr(List<Sotrudnik> sotr) {
        this.sotr = sotr;
    }

    public List<Sklad> getRashodniki() {
        return rashodniki;
    }

    public void setRashodniki(List<Sklad> rashodniki) {
        this.rashodniki = rashodniki;
    }

    @Override
    public String toString() {
        return "UslugaRashodnikiDto2{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", numbers=" + numbers +
                ", sotr=" + sotr +
                ", list=" + rashodniki +
                '}';
    }
}
