package ru.project.fotosalon.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PhotoFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private byte[] data;

    @ManyToOne
    private Portfolio portfolio;

    public PhotoFile(String name, byte[] data, Portfolio portfolio) {
        this.name = name;

        this.data = data;
        this.portfolio = portfolio;
    }

    public PhotoFile(Long id, String name, byte[] data, Portfolio portfolio) {
        this.id = id;
        this.name = name;

        this.data = data;
        this.portfolio = portfolio;
    }

    @Override
    public String toString() {
        return "PhotoFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", portfolio=" + portfolio +
                '}';
    }
}
