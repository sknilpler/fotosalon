package ru.project.fotosalon.dto;

import ru.project.fotosalon.models.Sotrudnik;

public class UserSotrudnik {

    private UserDto user;
    private Sotrudnik sotrudnik;

    public UserSotrudnik(UserDto user, Sotrudnik sotrudnik) {
        this.user = user;
        this.sotrudnik = sotrudnik;
    }

    public UserSotrudnik() {
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Sotrudnik getSotrudnik() {
        return sotrudnik;
    }

    public void setSotrudnik(Sotrudnik sotrudnik) {
        this.sotrudnik = sotrudnik;
    }

    @Override
    public String toString() {
        return "UserSotrudnik{" +
                "user=" + user +
                ", sotrudnik=" + sotrudnik +
                '}';
    }
}
