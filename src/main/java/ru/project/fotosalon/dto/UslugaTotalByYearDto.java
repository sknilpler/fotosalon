package ru.project.fotosalon.dto;

public class UslugaTotalByYearDto {

    private String month;
    private int num;
    private double total;

    public UslugaTotalByYearDto() {
    }

    public UslugaTotalByYearDto(String month, int num, double total) {
        this.month = month;
        this.num = num;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
        return "UslugaTotalByYearDto{" +
                "month='" + month + '\'' +
                ", num=" + num +
                ", total=" + total +
                '}';
    }
}
