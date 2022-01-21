package com.kirillz.KT2.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "users")
public class  CUser {
    @Id
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

    @OneToMany(mappedBy="owner", fetch = FetchType.EAGER)
    private List<COrder> orders;

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
        if (Objects.equals(gender, "Male"))
            this.gender = gender;
        else if (Objects.equals(gender, "Female"))
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
    //getter and setter for orders
    public List<COrder> getOrders() {
        return orders;
    }

    public void setOrders(List<COrder> orders) {
        this.orders = orders;
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
    public CUser(UUID id, String login, String name, String gender, LocalDate dateOfBirth, List <COrder> orders)
    {
        setId(id);
        setLogin(login);
        setName(name);
        setDateOfBirth(dateOfBirth);
        setGender(gender);
        this.setOrders(orders);
    }


}
