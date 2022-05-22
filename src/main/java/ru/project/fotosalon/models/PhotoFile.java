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

    private String url;

    @ManyToOne
    private Portfolio portfolio;

    public PhotoFile(String url, Portfolio portfolio) {
        this.url = url;
        this.portfolio = portfolio;
    }

    public PhotoFile(Long id, String url, Portfolio portfolio) {
        this.id = id;
        this.url = url;
        this.portfolio = portfolio;
    }
    @Transient
    public String getPhotoImagePath() {
        if (url == null || id == null) return null;

        return "/photos/portfolio/" + portfolio.getId() + "/" + url;
    }

    @Override
    public String toString() {
        return "PhotoFile{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", portfolio=" + portfolio +
                '}';
    }
}
