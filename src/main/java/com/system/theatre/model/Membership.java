package com.system.theatre.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Membership
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Наименование обязательно")
    @Size(min = 2, max = 50, message = "Значение должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё]+|[a-zA-Z]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    private String name;
    @Min(value = 1, message = "Минимальное значение 1")
    @Max(value = 1000000, message = "Максмимальное значение 1000000")
    @NotNull(message = "Значение не может быть пустым")
    private double price;
    @ManyToOne
    @JoinColumn(name = "performanceType_id")
    private PerformanceType performanceType;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id")
    private Author author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PerformanceType getPerformanceType() {
        return performanceType;
    }

    public void setPerformanceType(PerformanceType performanceType) {
        this.performanceType = performanceType;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
