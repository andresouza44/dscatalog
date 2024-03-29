package com.andresouza.dscatalog.dto;

import com.andresouza.dscatalog.entities.Product;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductDTO {
    private Long id;
    @Size(min=5, max= 15, message = "Nome deve entre 5 e 15 caracteres")
    private String name;
    private String description;

    @PositiveOrZero( message = "Deve ser maior ou igual a zero")

    private Double price;
    private String imgUrl;

    private List<Long> categoryId = new ArrayList<>();

    private Set<CategoryDTO> categories = new HashSet<>();

    public ProductDTO() {

    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(String name) {
        this.name = name;
    }


    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();

     //   entity.getCategories().forEach(category -> categoryId.add(category.getId()));

        // retirei a entidade categoria para ficar igual ao do curso
        entity.getCategories().forEach(category -> categories.add(new CategoryDTO(category)));
    }

   /* public List<Long> getCategoryId() {
        return categoryId;
    }
*/
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public Set<CategoryDTO> getCategories() {
        return categories;
    }
}
