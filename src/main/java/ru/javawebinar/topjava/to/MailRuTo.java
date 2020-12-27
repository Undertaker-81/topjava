package ru.javawebinar.topjava.to;

import java.util.Objects;

/**
 * @author Dmitriy Panfilov
 * 27.12.2020
 */
public class MailRuTo {
    private String nick;
    private String name;
    private String email;

    public MailRuTo(String nick, String name, String email) {
        this.nick = nick;
        this.name = name;
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailRuTo mailRuTo = (MailRuTo) o;
        return Objects.equals(nick, mailRuTo.nick) &&
                Objects.equals(name, mailRuTo.name) &&
                Objects.equals(email, mailRuTo.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nick, name, email);
    }
}
