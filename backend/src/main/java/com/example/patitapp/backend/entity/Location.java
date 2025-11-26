package com.example.patitapp.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer priceCLP;
    private String imageUrl;
    private Double lat;
    private Double lon;
    private String category;

    public Location() {
    }

    public Location(String name, String description, Integer priceCLP, String imageUrl, Double lat, Double lon, String category) {
        this.name = name;
        this.description = description;
        this.priceCLP = priceCLP;
        this.imageUrl = imageUrl;
        this.lat = lat;
        this.lon = lon;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriceCLP() {
        return priceCLP;
    }

    public void setPriceCLP(Integer priceCLP) {
        this.priceCLP = priceCLP;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
