// ScraperException.java
package com.synth.webscraper.exception;

public class ScraperException extends Exception {
    public ScraperException(String message) {
        super(message);
    }

    public ScraperException(String message, Throwable cause) {
        super(message, cause);
    }
}