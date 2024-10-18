
// JSoupProductScraper.java
package com.synth.webscraper.scraper;

import com.synth.webscraper.model.Product;
import com.synth.webscraper.config.ConfigLoader;
import com.synth.webscraper.exception.ScraperException;
import com.synth.webscraper.fetcher.JSoupPageFetcher;

import java.io.IOException;

import org.jsoup.nodes.Element;

public class JSoupProductScraper implements ProductScraper {
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
            String url = baseUrl + relativeUrl;
            String availability = productElement.select("div.product_price p.instock.availability").text().trim();
            String category = "";

             // Scrape category from the product detail page
             Element detailPage = JSoupPageFetcher.fetchPage(url);
             if (detailPage != null) {
                 category = detailPage.select("ul.breadcrumb li:nth-child(3) a").text();
             }
            // String name = productElement.select(".product-name").text();
            // String price = productElement.select(".product-price").text();
            // String rating = productElement.select(".product-rating").text();
            return new Product(name, price, rating, url, availability, category);
        } catch (Exception e) {
            throw new ScraperException("Error scraping product", e);
        }
    }
}