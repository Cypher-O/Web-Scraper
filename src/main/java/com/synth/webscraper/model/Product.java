// Product.java
package com.synth.webscraper.model;

public class Product {
    private final String id;
    private final String name;
    private final String price;
    private final String rating;
    private final String url;
    private final String availability;
    private final String category;

    public Product(String id, String name, String price, String rating, String url, String availability, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.url = url;
        this.availability = availability;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getUrl() {
        return url;
    }

    public String getAvailability() {
        return availability;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", rating='" + rating + '\'' +
                ", url='" + url + '\'' +
                ", availability='" + availability + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}