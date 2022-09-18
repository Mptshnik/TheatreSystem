package com.system.theatre.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Performance
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле обязательно")
    @Size(min = 2, max = 50, message = "Значение должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё\s]+|[a-zA-Z\s]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    private String name;
    @NotEmpty(message = "Поле обязательно")
    @Size(min = 2, max = 1000, message = "Значение должно находиться в диапазоне от 2 до 1000 символов")
    @Pattern(regexp = "^([а-яА-Яё\s]+|[a-zA-Z\s]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    private String description;
    @Future(message = "Дата должна быть больше текущей")
    @NotNull(message = "Поле обязательно")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate premieredate;

    @ManyToOne
    @JoinColumn(name = "performanceType_id")
    private PerformanceType performanceType;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_performance",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "performance_id",
                    referencedColumnName = "id"))
    private List<Employee> employees;

    @OneToMany(mappedBy = "performance")
    private List<SceneRole> sceneRoles;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @OneToMany(mappedBy = "performance")
    private List<Ticket> tickets;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<SceneRole> getSceneRoles() {
        return sceneRoles;
    }

    public void setSceneRoles(List<SceneRole> sceneRoles) {
        this.sceneRoles = sceneRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPremieredate() {
        return premieredate;
    }

    public void setPremieredate(LocalDate premieredate) {
        this.premieredate = premieredate;
    }

    public PerformanceType getPerformanceType() {
        return performanceType;
    }

    public void setPerformanceType(PerformanceType performanceType) {
        this.performanceType = performanceType;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
