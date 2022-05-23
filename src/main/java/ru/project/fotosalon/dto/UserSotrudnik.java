package ru.project.fotosalon.dto;

import ru.project.fotosalon.models.Role;
import ru.project.fotosalon.models.Sotrudnik;

import java.util.Set;

public class UserSotrudnik {

    private String username;
    private String password;
    private String fio;
    private String post;
    private String phone;
    private double oklad;
    private double premiya;
    private Set<Role> roles;

    public UserSotrudnik() {
    }

    public UserSotrudnik(String username, String password, String fio, String post, String phone, double oklad, double premiya, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.fio = fio;
        this.post = post;
        this.phone = phone;
        this.oklad = oklad;
        this.premiya = premiya;
        this.roles = roles;
    }

    public UserSotrudnik(String username, String password, String fio, String post, String phone, double oklad, double premiya) {
        this.username = username;
        this.password = password;
        this.fio = fio;
        this.post = post;
        this.phone = phone;
        this.oklad = oklad;
        this.premiya = premiya;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getOklad() {
        return oklad;
    }

    public void setOklad(double oklad) {
        this.oklad = oklad;
    }

    public double getPremiya() {
        return premiya;
    }

    public void setPremiya(double premiya) {
        this.premiya = premiya;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserSotrudnik{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fio='" + fio + '\'' +
                ", post='" + post + '\'' +
                ", phone='" + phone + '\'' +
                ", oklad=" + oklad +
                ", premiya=" + premiya +
                ", roles=" + roles +
                '}';
    }
//    private UserDto user;
//    private Sotrudnik sotrudnik;
//
//    public UserSotrudnik(UserDto user, Sotrudnik sotrudnik) {
//        this.user = user;
//        this.sotrudnik = sotrudnik;
//    }
//
//    public UserSotrudnik() {
//    }
//
//    public UserDto getUser() {
//        return user;
//    }
//
//    public void setUser(UserDto user) {
//        this.user = user;
//    }
//
//    public Sotrudnik getSotrudnik() {
//        return sotrudnik;
//    }
//
//    public void setSotrudnik(Sotrudnik sotrudnik) {
//        this.sotrudnik = sotrudnik;
//    }
//
//    @Override
//    public String toString() {
//        return "UserSotrudnik{" +
//                "user=" + user +
//                ", sotrudnik=" + sotrudnik +
//                '}';
//    }
}
