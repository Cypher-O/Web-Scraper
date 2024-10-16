
// JSoupProductScraper.java
package com.synth.webscraper.scraper;

import com.synth.webscraper.model.Product;
import com.synth.webscraper.exception.ScraperException;
import org.jsoup.nodes.Element;

public class JSoupProductScraper implements ProductScraper {
    @Override
    public Product scrapeProduct(Element productElement) throws ScraperException {
        try {
            String name = productElement.select(".product-name").text();
            String price = productElement.select(".product-price").text();
            String rating = productElement.select(".product-rating").text();
            return new Product(name, price, rating);
        } catch (Exception e) {
            throw new ScraperException("Error scraping product", e);
        }
    }
}