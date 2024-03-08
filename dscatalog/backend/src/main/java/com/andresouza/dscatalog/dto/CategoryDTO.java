package com.andresouza.dscatalog.dto;

import com.andresouza.dscatalog.entities.Category;
import com.andresouza.dscatalog.entities.Product;

import java.util.*;

public class CategoryDTO {
    private Long id;
    private String name;

  //  private List<ProductDTO> products = new ArrayList<>();

    public  CategoryDTO (){

    }


    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity){
        id = entity.getId();
        name = entity.getName();

  //   entity.getProducts().forEach(product -> products.add(new ProductDTO(product)));


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

 /*   public List<ProductDTO> getProducts() {
        return products;
    }
 */
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
