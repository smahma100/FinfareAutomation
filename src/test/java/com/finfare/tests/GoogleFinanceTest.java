package com.finfare.tests;

import com.finfare.config.ConfigManager;
import com.finfare.factory.DriverFactory;
import com.finfare.pages.GoogleFinancePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoogleFinanceTest {
    private WebDriver driver;
    private GoogleFinancePage googleFinancePage;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get(ConfigManager.getProperty("base.url"));
        googleFinancePage = new GoogleFinancePage(driver);
    }

    @Test
    public void verifyStockSymbolsInYouMayBeInterestedInSection() {
        // Step 1: Verify the page title
        Assert.assertEquals(googleFinancePage.getPageTitle(), "Google Finance");

        // Step 2: Retrieve actual symbols and compare with expected test data
        List<String> actualSymbols = googleFinancePage.getActualStockSymbols();
        List<String> expectedSymbols = Arrays.asList("NFLX", "MSFT", "TSLA");

        // Step 3: Find differences between actual and expected symbols
        List<String> symbolsOnlyInActual = actualSymbols.stream()
                .filter(symbol -> !expectedSymbols.contains(symbol))
                .collect(Collectors.toList());

        List<String> symbolsOnlyInExpected = expectedSymbols.stream()
                .filter(symbol -> !actualSymbols.contains(symbol))
                .collect(Collectors.toList());

        System.out.println("Symbols only in Google Finance: " + symbolsOnlyInActual);
        System.out.println("Symbols only in Test Data: " + symbolsOnlyInExpected);

        // Assertions for symbol validation
        Assert.assertTrue(symbolsOnlyInActual.isEmpty(), "Unexpected symbols found in Google Finance: " + symbolsOnlyInActual);
        Assert.assertTrue(symbolsOnlyInExpected.isEmpty(), "Expected symbols not found in Google Finance: " + symbolsOnlyInExpected);
    }

    @Test
    public void verifyNegativeStockSymbolsAndPrices() {
        // Expected negative stock symbols
        List<String> expectedNegativeSymbols = googleFinancePage.getExpectedNegativeStockSymbols();

        // Retrieve actual negative stock symbols and their prices
        List<String> actualNegativeSymbols = googleFinancePage.getActualNegativeStockSymbols();
        List<Double> actualNegativePrices = googleFinancePage.getPricesOfActualNegativeStockSymbols();

        // Check if all expected negative symbols are in actual list
        Assert.assertTrue(actualNegativeSymbols.containsAll(expectedNegativeSymbols), "Some expected negative stock symbols are missing.");

        // Verify prices are non-zero for negative symbols
        for (Double price : actualNegativePrices) {
            Assert.assertTrue(price > 0, "Price for a negative stock symbol should be greater than 0.");
        }
    }

    @Test
    public void verifyPositiveStockSymbolsAndPrices() {
        // Retrieve positive stock symbols and prices as a map
        Map<String, Double> positiveStocksWithPrices = googleFinancePage.getPositiveStockSymbolsWithPrices();

        // Verify each symbol has a valid, non-zero price
        for (Map.Entry<String, Double> entry : positiveStocksWithPrices.entrySet()) {
            Assert.assertTrue(entry.getValue() > 0, "Price for positive stock symbol " + entry.getKey() + " should be greater than 0.");
            System.out.println("Positive Stock Symbol: " + entry.getKey() + ", Price: " + entry.getValue());
        }
    }

    @Test
    public void verifyStockDescriptions() {
        // Retrieve and print stock descriptions
        List<String> stockDescriptions = googleFinancePage.getStockDescriptions();
        Assert.assertFalse(stockDescriptions.isEmpty(), "Stock descriptions should not be empty.");

        System.out.println("Stock Descriptions:");
        stockDescriptions.forEach(System.out::println);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
