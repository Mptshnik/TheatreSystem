package com.system.theatre.model;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Ticket
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int seat;
    private int row;

    @Min(value = 1, message = "Минимальное значение 1")
    @Max(value = 1000000, message = "Максмимальное значение 1000000")
    @NotNull(message = "Значение не может быть пустым")
    private double price;

    @Future
    private LocalDateTime beginningtime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performance_id")
    @NotNull(message = "Значение не может быть пустым")
    private Performance performance;
    private boolean sold;

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
