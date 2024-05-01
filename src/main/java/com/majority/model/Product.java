package com.majority.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private double price;
    @ElementCollection
    private List<String> keywords;
    @OneToMany
    private List<Image> images;
    public Product(){}

    public Product(Long id, String name, String category, double price, List<String> keywords) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.keywords = keywords;
    }

    public Product(String name, String category, double price, List<String> keywords) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.keywords = keywords;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
