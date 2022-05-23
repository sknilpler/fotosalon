package ru.project.fotosalon.dto;

import java.util.List;

public class AddGrafikDto {

    private Long idSotr;

    private List<String> dates;

    public AddGrafikDto() {
    }

    public AddGrafikDto(Long idSotr, List<String> dates) {
        this.idSotr = idSotr;
        this.dates = dates;
    }

    public Long getIdSotr() {
        return idSotr;
    }

    public void setIdSotr(Long idSotr) {
        this.idSotr = idSotr;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "AddGrafikDto{" +
                "idSotr=" + idSotr +
                ", dates=" + dates +
                '}';
    }
}
