
// CSVWriterImpl.java
package com.synth.webscraper.writer;

import com.synth.webscraper.model.Product;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CSVWriterImpl implements CSVWriter {
    @Override
    public void saveToCSV(List<Product> products) throws IOException {
        String userHome = System.getProperty("user.home");
        Path downloadsPath = Paths.get(userHome, "Downloads", "product_data.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(downloadsPath)) {
            writer.write("_id,Product Name,Price,Rating,URL,Availability,Category\n");
            for (Product product : products) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                        escapeSpecialCharacters(String.valueOf(product.getId())),
                        escapeSpecialCharacters(product.getName()),
                        escapeSpecialCharacters(product.getPrice()),
                        escapeSpecialCharacters(product.getRating()),
                        escapeSpecialCharacters(product.getUrl()),
                        escapeSpecialCharacters(product.getAvailability()),
                        escapeSpecialCharacters(product.getCategory())));
            }
        }
    }

    private String escapeSpecialCharacters(String input) {
        if (input == null) {
            return "";
        }
        String escaped = input.replace("\"", "\"\"");
        return escaped.contains(",") ? "\"" + escaped + "\"" : escaped;
    }
}