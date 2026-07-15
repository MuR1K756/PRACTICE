package com.example.demo.restaurant.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "dishes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Dish {

    @Min(value = 0, message = "Цена не может быть отрицательной")
    private Double price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DishCategory category;

    @Column(nullable = false)
    private Boolean isActive; // true = в наличии, false = в стоп-листе
}