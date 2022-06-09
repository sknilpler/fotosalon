package ru.project.fotosalon.dto;

public class DatesDto {

    private String date;

    public DatesDto() {
    }

    public DatesDto(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DatesDto{" +
                "date='" + date + '\'' +
                '}';
    }
}
