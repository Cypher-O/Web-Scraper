# Web Scraper - Product Data Extractor

This project is a Java-based web scraper designed to extract product information from an online bookstore. It uses JSoup for HTML parsing and provides a concurrent scraping mechanism for improved performance.

---

## Features

- Scrapes product details including name, price, rating, availability, and category
- Supports pagination to scrape multiple pages
- Concurrent scraping using Java's ExecutorService
- Exports data to CSV format
- Configurable through a properties file

---

## Prerequisites

- **Java 11+**  
- **Maven/Gradle** (for dependency management)
- **JSoup** for HTML parsing
- **SLF4J** for logging
- **JavaFX** for the user interface

---

## Getting Started

### 1. Clone the Repository

 ```sh
    git clone https://github.com/Cypher-O/web-scraper.git
    cd web-scraper
 ```

### 2. Setup Configuration

Create a `config.properties` file with the following content in the resources directory:

 ```sh
    url=http://books.toscrape.com/
    csvFilePath=product_data.csv
 ```

### 3. Install Dependencies

Use `Maven` to download all required dependencies:

 ```sh
    mvn clean install
 ```

## Project Structure

src/
├── main/
│   ├── java/
│   │   ├── com/
│   │   │   └── synth/
│   │   │       ├── config/
│   │   │       │   └── ConfigLoader.java
│   │   │       ├── exception/
│   │   │       │   └── ScraperException.java
│   │   │       ├── fetcher/
│   │   │       │   └── JSoupPageFetcher.java
│   │   │       ├── model/
│   │   │       │   └── Product.java
│   │   │       ├── scraper/
│   │   │       │   ├── JSoupProductScraper.java
│   │   │       │   └── ProductScraper.java
│   │   │       ├── writer/
│   │   │       │   ├── CSVWriter.java
│   │   │       │   └── CSVWriterImpl.java
│   │   │       └── WebScraper.java
│   └── resources/
│       └── config.properties
└── test/

## Directory Details

- **main/java/com/synth/**: Contains the main Java source code.
  - **config/**: Configuration-related classes.
    - `ConfigLoader.java`: Loads application configurations.
  - **exception/**: Custom exception classes.
    - `ScraperException.java`: Handles exceptions related to the web scraping process.
  - **fetcher/**: Classes responsible for fetching web pages.
    - `JSoupPageFetcher.java`: Uses JSoup to fetch HTML pages.
  - **model/**: Data model classes.
    - `Product.java`: Represents a product entity.
  - **scraper/**: Web scraping logic.
    - `JSoupProductScraper.java`: Scrapes product information using JSoup.
    - `ProductScraper.java`: General product scraping functionalities.
  - **writer/**: Classes for writing data.
    - `CSVWriter.java`: Interface for writing to CSV.
    - `CSVWriterImpl.java`: Implementation of CSV writing logic.
  - **WebScraper.java**: Main entry point for the web scraping functionality.

- **main/resources/**: Contains resource files.
  - `config.properties`: Configuration properties for the application.

- **test/**: This directory will contain unit tests and test resources.
  
Configuration
The project uses a config.properties file for configuration. Ensure this file is present in the resources directory with the following properties:
Copyurl=<http://books.toscrape.com/>
csvFilePath=product_data.csv
Usage
To run the web scraper:

Compile the project using Maven:
Copymvn clean package

Run the WebScraper class:
Copyjava -cp target/webscraper-1.0-SNAPSHOT.jar com.synth.webscraper.WebScraper

The scraped data will be saved in the Downloads directory as product_data.csv.
Contributing
Contributions are welcome! Please feel free to submit a Pull Request.
License
This project is open source and available under the MIT License.
