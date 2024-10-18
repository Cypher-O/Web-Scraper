
// JSoupProductScraper.java
package com.synth.webscraper.scraper;

import com.synth.webscraper.model.Product;
import com.synth.webscraper.config.ConfigLoader;
import com.synth.webscraper.exception.ScraperException;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JSoupProductScraper implements ProductScraper {
    private static final Logger logger = LoggerFactory.getLogger(JSoupProductScraper.class);
    private final String baseUrl;

    public JSoupProductScraper(String configPath) throws IOException {
        ConfigLoader config = new ConfigLoader(configPath);
        this.baseUrl = config.getProperty("url");
    }

    @Override
    public Product scrapeProduct(Element productElement) throws ScraperException {
        try {
            String name = productElement.select("h3 a").attr("title");
            String price = productElement.select("div.product_price p.price_color").text();
            String rating = productElement.select("p.star-rating").first().className().split(" ")[1];
            String relativeUrl = productElement.select("h3 a").attr("href");
            String url = baseUrl + "catalogue/" + relativeUrl;
            String availability = productElement.select("div.product_price p.instock.availability").text().trim();
            
            // Extract category from the URL
            String category = relativeUrl.split("/")[0].replace("-", " ");

            return new Product(name, price, rating, url, availability, category);
        } catch (Exception e) {
            logger.warn("Error scraping product", e);
            return null;
        }
    }
}