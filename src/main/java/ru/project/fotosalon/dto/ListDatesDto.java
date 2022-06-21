package ru.project.fotosalon.dto;

import java.util.List;

public class ListDatesDto {
    private List<DatesDto> dates;

    public ListDatesDto() {
    }

    public ListDatesDto(List<DatesDto> dates) {
        this.dates = dates;
    }

    public List<DatesDto> getDates() {
        return dates;
    }

    public void setDates(List<DatesDto> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "ListDatesDto{" +
                "dates=" + dates +
                '}';
    }
}
