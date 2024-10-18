
// CSVWriterImpl.java
package com.synth.webscraper.writer;

import com.synth.webscraper.model.Product;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriterImpl implements CSVWriter {
    @Override
    public void saveToCSV(List<Product> products, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Product Name,Price,Rating,URL,Availability,Category\n");
            for (Product product : products) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
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