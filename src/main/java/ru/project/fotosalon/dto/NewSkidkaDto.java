package ru.project.fotosalon.dto;

public class NewSkidkaDto {
    private Long idUslugi;
    private int skidka;
    private String basis;

    public NewSkidkaDto() {
    }

    public NewSkidkaDto(Long idUslugi, int skidka, String basis) {
        this.idUslugi = idUslugi;
        this.skidka = skidka;
        this.basis = basis;
    }

    public void setIdUslugi(Long idUslugi) {
        this.idUslugi = idUslugi;
    }

    public void setSkidka(int skidka) {
        this.skidka = skidka;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public Long getIdUslugi() {
        return idUslugi;
    }

    public int getSkidka() {
        return skidka;
    }

    public String getBasis() {
        return basis;
    }

    @Override
    public String toString() {
        return "NewSkidkaDto{" +
                "idUslugi=" + idUslugi +
                ", skidka=" + skidka +
                ", basis='" + basis + '\'' +
                '}';
    }
}
