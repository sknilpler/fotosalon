package ru.project.fotosalon.dto;

public class SkidkaDto {

    private Long id;
    private String basis;
    private double size;
    private Long id_sotr;
    private Long id_clienta;
    private Long id_uslugi;

    public SkidkaDto() {
    }


    public SkidkaDto(String basis, double size, Long id_sotr, Long id_clienta, Long id_uslugi) {
        this.basis = basis;
        this.size = size;
        this.id_sotr = id_sotr;
        this.id_clienta = id_clienta;
        this.id_uslugi = id_uslugi;
    }

    public SkidkaDto(Long id, String basis, double size, Long id_sotr, Long id_clienta, Long id_uslugi) {
        this.id = id;
        this.basis = basis;
        this.size = size;
        this.id_sotr = id_sotr;
        this.id_clienta = id_clienta;
        this.id_uslugi = id_uslugi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Long getId_sotr() {
        return id_sotr;
    }

    public void setId_sotr(Long id_sotr) {
        this.id_sotr = id_sotr;
    }

    public Long getId_clienta() {
        return id_clienta;
    }

    public void setId_clienta(Long id_clienta) {
        this.id_clienta = id_clienta;
    }

    public Long getId_uslugi() {
        return id_uslugi;
    }

    public void setId_uslugi(Long id_uslugi) {
        this.id_uslugi = id_uslugi;
    }

    @Override
    public String toString() {
        return "SkidkaDto{" +
                "id=" + id +
                ", basis='" + basis + '\'' +
                ", size=" + size +
                ", id_sotr=" + id_sotr +
                ", id_clienta=" + id_clienta +
                ", id_uslugi=" + id_uslugi +
                '}';
    }
}
