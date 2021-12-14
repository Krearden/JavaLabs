package com.kirillz.KT2.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "users")
public class CUser {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "login")
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
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
        if (gender == "Male")
            this.gender = gender;
        else if (gender == "Female")
            this.gender = gender;
    }
    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }
    public static int getAge(LocalDate dateOfBirth)
    {
        LocalDate now = LocalDate.now();
        return now.getYear() - dateOfBirth.getYear();
    }

    //путой конструктор
    public CUser()
    {
        id = null;
        gender = "undifinded";
        dateOfBirth = LocalDate.now();
        login = "";
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
