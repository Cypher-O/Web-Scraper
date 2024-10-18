// WebScraper.java

package com.synth.webscraper;

import com.synth.webscraper.config.ConfigLoader;
import com.synth.webscraper.model.Product;
import com.synth.webscraper.scraper.ProductScraper;
import com.synth.webscraper.scraper.JSoupProductScraper;
import com.synth.webscraper.writer.CSVWriter;
import com.synth.webscraper.writer.CSVWriterImpl;
import com.synth.webscraper.exception.ScraperException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WebScraper {
    private static final Logger logger = LoggerFactory.getLogger(WebScraper.class);
    private final String baseUrl;
    private final String csvFilePath;
    private final ProductScraper productScraper;
    private final CSVWriter csvWriter;

    public WebScraper(String configPath) throws IOException {
        ConfigLoader config = new ConfigLoader(configPath);
        this.baseUrl = config.getProperty("url");
        this.csvFilePath = config.getProperty("csvFilePath");
        this.productScraper = new JSoupProductScraper(configPath);
        this.csvWriter = new CSVWriterImpl();
    }

    public void run() {
        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Product> allProducts = new ArrayList<>();
            
            String nextPageUrl = baseUrl + "catalogue/page-1.html";
            int pageNumber = 1;
            
            while (nextPageUrl != null) {
                List<Product> pageProducts = scrapeProductsFromPage(executor, nextPageUrl);
                allProducts.addAll(pageProducts);
                
                logger.info("Scraped {} products from page {}", pageProducts.size(), pageNumber);
                
                nextPageUrl = getNextPageUrl(nextPageUrl);
                pageNumber++;
            }

            csvWriter.saveToCSV(allProducts, csvFilePath);
            logger.info("Data has been successfully scraped and saved to {}", csvFilePath);
        } catch (IOException e) {
            logger.error("Error occurred during web scraping", e);
        } catch (ScraperException e) {
            logger.error("Scraper specific error occurred", e);
        } finally {
            if (executor != null) {
                shutdownExecutor(executor);
            }
        }
    }

    private List<Product> scrapeProductsFromPage(ExecutorService executor, String pageUrl) throws IOException, ScraperException {
        Document document = Jsoup.connect(pageUrl).get();
        Elements productElements = document.select("article.product_pod");
        
        List<Future<Product>> futures = new ArrayList<>();

        for (Element productElement : productElements) {
            futures.add(executor.submit(() -> productScraper.scrapeProduct(productElement)));
        }

        List<Product> products = new ArrayList<>();
        for (Future<Product> future : futures) {
            try {
                Product product = future.get();
                if (product != null) {
                    products.add(product);
                }
            } catch (Exception e) {
                logger.warn("Error scraping a product", e);
            }
        }

        return products;
    }

    private String getNextPageUrl(String currentPageUrl) throws IOException {
        Document document = Jsoup.connect(currentPageUrl).get();
        Element nextButton = document.selectFirst("li.next a");
        if (nextButton != null) {
            String relativeUrl = nextButton.attr("href");
            return baseUrl + "catalogue/" + relativeUrl;
        }
        return null;
    }

    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("Executor did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        String configPath = args.length > 0 ? args[0] : "/config.properties";
        try {
            WebScraper scraper = new WebScraper(configPath);
            scraper.run();
        } catch (IOException e) {
            logger.error("Error initializing WebScraper", e);
            System.exit(1);
        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
            System.exit(1);
        }
    }
}