package com.system.theatre.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class SceneRole
{
    @Size(min = 2, max = 50, message = "Значение должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё]+|[a-zA-Z]+|[0-9]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита или цифры 0-9")
    @Column(unique = true)
    @NotEmpty(message = "Значение обязательно")
    private String name;

    @ManyToOne
    @NotNull(message = "Значение обязательно")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @NotNull(message = "Значение обязательно")
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
