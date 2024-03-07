package com.andresouza.dscatalog.dto;

import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.entities.Product;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CategoryDTO {
    private Long id;
    private String name;

    private Set<Product> products = new HashSet<>();

    public  CategoryDTO (){

    }

    public CategoryDTO(Category entity){
        id = entity.getId();
        name = entity.getName();

        entity.getProducts().stream()
                .map(product -> products.add(product));
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDTO that = (CategoryDTO) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
