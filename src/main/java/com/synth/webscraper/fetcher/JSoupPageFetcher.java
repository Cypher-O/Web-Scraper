package com.synth.webscraper.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JSoupPageFetcher {
    private static final Logger logger = LoggerFactory.getLogger(JSoupPageFetcher.class);

    public static Element fetchPage(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();
            return doc.body();
        } catch (IOException e) {
            logger.error("Error fetching page: " + url, e);
            return null;
        }
    }
}