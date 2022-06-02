package ru.project.fotosalon.dto;

public class RashodnikDto {
    private Long id_sklad;
    private Long id_uslugi;
    private double numbers;

    public RashodnikDto() {
    }

    public RashodnikDto(Long id_sklad, Long id_uslugi, double numbers) {
        this.id_sklad = id_sklad;
        this.id_uslugi = id_uslugi;
        this.numbers = numbers;
    }

    public RashodnikDto(Long id_sklad, double numbers) {
        this.id_sklad = id_sklad;
        this.numbers = numbers;
    }

    public Long getId_sklad() {
        return id_sklad;
    }

    public void setId_sklad(Long id_sklad) {
        this.id_sklad = id_sklad;
    }

    public Long getId_uslugi() {
        return id_uslugi;
    }

    public void setId_uslugi(Long id_uslugi) {
        this.id_uslugi = id_uslugi;
    }

    public double getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return "RashodnikDto{" +
                "id_sklad=" + id_sklad +
                ", id_uslugi=" + id_uslugi +
                ", numbers=" + numbers +
                '}';
    }

    public void setNumbers(double numbers) {
        this.numbers = numbers;
    }
}
