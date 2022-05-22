package ru.project.fotosalon.dto;

public class PortfolioDto {

    private String about;
    private Long idSotr;

    public PortfolioDto() {
    }

    public PortfolioDto(String about, Long idSotr) {
        this.about = about;
        this.idSotr = idSotr;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Long getIdSotr() {
        return idSotr;
    }

    public void setIdSotr(Long idSotr) {
        this.idSotr = idSotr;
    }

    @Override
    public String toString() {
        return "PortfolioDto{" +
                "about='" + about + '\'' +
                ", idSotr=" + idSotr +
                '}';
    }
}
