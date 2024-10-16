// ProductScraper.java
package com.synth.webscraper.scraper;

import com.synth.webscraper.model.Product;
import com.synth.webscraper.exception.ScraperException;
import org.jsoup.nodes.Element;

public interface ProductScraper {
    Product scrapeProduct(Element productElement) throws ScraperException;
}
