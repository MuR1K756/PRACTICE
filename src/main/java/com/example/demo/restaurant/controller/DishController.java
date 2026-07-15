package com.example.demo.restaurant.controller;

import com.example.demo.restaurant.model.Dish;
import com.example.demo.restaurant.model.DishCategory;
import com.example.demo.restaurant.repository.DishRepository;
import com.example.demo.restaurant.specification.DishSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "*") // Разрешаем запросы с React-фронтенда
public class DishController {

    @Autowired
    private DishRepository dishRepository;

    // Получение списка с динамической фильтрацией
    @GetMapping
    public List<Dish> getAllDishes(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) DishCategory category,
            @RequestParam(required = false) Boolean isActive) {

        Specification<Dish> spec = Specification.where(DishSpecifications.hasSearchText(search))
                .and(DishSpecifications.hasCategory(category))
                .and(DishSpecifications.hasActiveStatus(isActive));

        return dishRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public Dish getDishById(@PathVariable Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Блюдо не найдено"));
    }

    @PostMapping
    public Dish createDish(@Valid @RequestBody Dish dish) {    
        return dishRepository.save(dish);
    }

    @PutMapping("/{id}")
    public Dish updateDish(@PathVariable Long id, @RequestBody Dish dishDetails) {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Блюдо не найдено"));
        dish.setName(dishDetails.getName());
        dish.setDescription(dishDetails.getDescription());
        dish.setCategory(dishDetails.getCategory());
        dish.setPrice(dishDetails.getPrice());
        dish.setIsActive(dishDetails.getIsActive());
        return dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable Long id) {
        dishRepository.deleteById(id);
    }
}
