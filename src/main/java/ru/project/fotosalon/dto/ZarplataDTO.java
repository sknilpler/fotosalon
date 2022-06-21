package ru.project.fotosalon.dto;

public class ZarplataDTO {
    private Long   id;
    private String username;
    private String fio;
    private String post;
    private String phone;
    private double oklad;
    private double premiya;
    private String avatar;
    private int    hours;
    private double zarplata;

    public ZarplataDTO(Long id, String username, String fio, String post, String phone, double oklad, double premiya, String avatar, int hours, double zarplata) {
        this.id = id;
        this.username = username;
        this.fio = fio;
        this.post = post;
        this.phone = phone;
        this.oklad = oklad;
        this.premiya = premiya;
        this.avatar = avatar;
        this.hours = hours;
        this.zarplata = zarplata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getZarplata() {
        return zarplata;
    }

    public void setZarplata(double zarplata) {
        this.zarplata = zarplata;
    }

    @Override
    public String toString() {
        return "ZarplataDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fio='" + fio + '\'' +
                ", post='" + post + '\'' +
                ", phone='" + phone + '\'' +
                ", oklad=" + oklad +
                ", premiya=" + premiya +
                ", avatar='" + avatar + '\'' +
                ", hours=" + hours +
                ", zarplata=" + zarplata +
                '}';
    }
}
