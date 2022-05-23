package ru.project.fotosalon.dto;

import ru.project.fotosalon.models.Client;
import ru.project.fotosalon.models.Sotrudnik;

import javax.persistence.Transient;

public class UserClient {
    private String username;
    private String password;
    private String fio;
    private String email;
    private String phone;

    public UserClient() {
    }

    public UserClient(String username, String password, String fio, String email, String phone) {
        this.username = username;
        this.password = password;
        this.fio = fio;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserClient{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fio='" + fio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    //    private UserDto user;
//    private Client client;
//
//    public UserClient(UserDto user, Client client) {
//        this.user = user;
//        this.client = client;
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
//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }
//
//    public UserClient() {
//    }
//
//    @Override
//    public String toString() {
//        return "UserClient{" +
//                "user=" + user +
//                ", client=" + client +
//                '}';
//    }
}
