package ru.project.fotosalon.dto;

public class MessageDto {
    private String email;
    private String title;
    private String text;

    public MessageDto() {
    }

    public MessageDto(String email, String title, String text) {
        this.email = email;
        this.title = title;
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
