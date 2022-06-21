package ru.project.fotosalon.dto;

public class IdsDto {
    private Long id;

    public IdsDto() {
    }

    public IdsDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdsDto{" +
                "id=" + id +
                '}';
    }
}
