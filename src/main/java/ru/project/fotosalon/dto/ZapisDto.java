package ru.project.fotosalon.dto;

import java.util.List;

public class ZapisDto {

    private Long id_sotr;
    private Long id_client;
    private Long id_usl;

    private List<IdsDto> grafiks;

    public ZapisDto() {
    }

    public ZapisDto(Long id_sotr, Long id_client, Long id_usl, List<IdsDto> grafiks) {
        this.id_sotr = id_sotr;
        this.id_client = id_client;
        this.id_usl = id_usl;
        this.grafiks = grafiks;
    }

    public Long getId_sotr() {
        return id_sotr;
    }

    public void setId_sotr(Long id_sotr) {
        this.id_sotr = id_sotr;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    public Long getId_usl() {
        return id_usl;
    }

    public void setId_usl(Long id_usl) {
        this.id_usl = id_usl;
    }

    public List<IdsDto> getGrafiks() {
        return grafiks;
    }

    public void setGrafiks(List<IdsDto> grafiks) {
        this.grafiks = grafiks;
    }

    @Override
    public String toString() {
        return "ZapisDto{" +
                "id_sotr=" + id_sotr +
                ", id_client=" + id_client +
                ", id_usl=" + id_usl +
                ", grafiks=" + grafiks +
                '}';
    }
}
