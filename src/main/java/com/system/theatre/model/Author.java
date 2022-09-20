package com.system.theatre.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Author
{
    @NotEmpty(message = "Фамилия обязательна")
    @Size(min = 2, max = 50, message = "Значение должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё0-9]+|[a-zA-Z0-9]+)$",
            message = "Значение должно содержать цифры, буквы русского или латинского алфавита")
    private String lastname;
    @NotEmpty(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Значени должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё]+|[a-zA-Z]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    private String firstname;

    @Size(max = 50, message = "Значение должно находиться в диапазоне от 0 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё]+|[a-zA-Z]+|\s*)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    private String middlename;

    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy = "author")
    private Set<Performance> performances;

    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy = "author")
    private Set<Membership> memberships;

    @PreRemove
    private void preRemove() {
        performances.forEach(performance -> performance.setAuthor(null));
        memberships.forEach(membership -> membership.setAuthor(null));
    }

    public Author()
    {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
