// Product.java
package com.synth.webscraper.model;

public class Product {
    private final String name;
    private final String price;
    private final String rating;

    public Product(String name, String price, String rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}