package ru.project.fotosalon.dto;

public class BasisSkidkaDto {
    private Long id;
    private int size;
    private String basis;

    public BasisSkidkaDto() {
    }

    public BasisSkidkaDto(Long id, int size, String basis) {
        this.id = id;
        this.size = size;
        this.basis = basis;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "BasisSkidkaDto{" +
                "id=" + id +
                ", size=" + size +
                ", basis='" + basis + '\'' +
                '}';
    }
}
