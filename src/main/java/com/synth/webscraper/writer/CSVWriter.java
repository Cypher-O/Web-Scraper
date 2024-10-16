// CSVWriter.java
package com.synth.webscraper.writer;

import com.synth.webscraper.model.Product;
import java.io.IOException;
import java.util.List;

public interface CSVWriter {
    void saveToCSV(List<Product> products, String filePath) throws IOException;
}