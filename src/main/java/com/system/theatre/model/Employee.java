package com.system.theatre.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class Employee
{
    @NotEmpty(message = "Фамилия обязательна")
    @Size(min = 2, max = 50, message = "Значение должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё]+|[a-zA-Z]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    private String lastname;
    @NotEmpty(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Значени должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё]+|[a-zA-Z]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    private String firstname;
    @Size(min = 2, max = 50, message = "Значение должно находиться в диапазоне от 2 до 50 символов")
    @Pattern(regexp = "^([а-яА-Яё]+|[a-zA-Z]+)$",
            message = "Значение должно содержать буквы русского или латинского алфавита")
    @Null
    private String middlename;

    @Null
    @Min(value = 50, message = "Миниимальное значение 50")
    @Max(value = 300, message = "Максимально значение 300")
    private double height;

    @Null
    @Min(value = 1, message = "Миниимальное значение 1")
    @Max(value = 150, message = "Максимально значение 150")
    private int age;
    @Null
    private boolean ontour;
    @Null
    private boolean isstudent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @Null
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voiceType_id")
    private VoiceType voiceType;

    @Null
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @Null
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "employees")
    private List<Performance> performances;

    @OneToMany(mappedBy = "employee")
    private List<SceneRole> sceneRoles;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public List<SceneRole> getSceneRoles() {
        return sceneRoles;
    }

    public void setSceneRoles(List<SceneRole> sceneRoles) {
        this.sceneRoles = sceneRoles;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public VoiceType getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(VoiceType voiceType) {
        this.voiceType = voiceType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isOntour() {
        return ontour;
    }

    public void setOntour(boolean ontour) {
        this.ontour = ontour;
    }

    public boolean isIsstudent() {
        return isstudent;
    }

    public void setIsstudent(boolean isstudent) {
        this.isstudent = isstudent;
    }


    public Employee()
    {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
