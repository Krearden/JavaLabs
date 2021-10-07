package com.kirillz;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

public class CUser {
    private UUID id;
    private String login;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;

    //геттеры, сеттеры и функции
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}
    public String getLogin() {return login;}
    public void setLogin(String login)
    {
        if (login.length() <= 50)
            this.login = login;
    }
    public String getName() {return name;}
    public void setName(String name)
    {
        if (name.length() <= 50)
            this.name = name;
    }
    public String getGender() {return gender;}
    public void setGender(String gender)
    {
        if (gender.length() == 1)
            this.gender = gender;
    }
    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        int age  = CUser.getAge(dateOfBirth);
        if (age < 150 && age >= 14)
            this.dateOfBirth = dateOfBirth;
    }
    public static int getAge(LocalDate dateOfBirth)
    {
        LocalDate now = LocalDate.now();
        return now.getYear() - dateOfBirth.getYear();
    }

    //конструктор класса
    public CUser(UUID id, String login, String name, String gender, LocalDate dateOfBirth)
    {
        setId(id);
        setLogin(login);
        setName(name);
        setDateOfBirth(dateOfBirth);
        setGender(gender);
    }
}
