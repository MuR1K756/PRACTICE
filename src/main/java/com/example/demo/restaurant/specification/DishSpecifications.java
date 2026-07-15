package com.example.demo.restaurant.specification;

import com.example.demo.restaurant.model.Dish;
import com.example.demo.restaurant.model.DishCategory;
import org.springframework.data.jpa.domain.Specification;

public class DishSpecifications {

    // Поиск по подстроке в имени или описании (без учета регистра)
    public static Specification<Dish> hasSearchText(String text) {
        return (root, query, cb) -> {
            if (text == null || text.trim().isEmpty()) return null;
            String likePattern = "%" + text.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("name")), likePattern),
                cb.like(cb.lower(root.get("description")), likePattern)
            );
        };
    }

    // Фильтр по категории
    public static Specification<Dish> hasCategory(DishCategory category) {
        return (root, query, cb) -> category == null ? null : cb.equal(root.get("category"), category);
    }

    // Фильтр по доступности (в наличии / стоп-лист)
    public static Specification<Dish> hasActiveStatus(Boolean isActive) {
        return (root, query, cb) -> isActive == null ? null : cb.equal(root.get("isActive"), isActive);
    }
}
