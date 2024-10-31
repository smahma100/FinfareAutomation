package com.finfare.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GoogleFinancePage extends BasePage {

    private List<String> expectedStockSymbols = Arrays.asList("NFLX", "MSFT", "TSLA", "EKGYO", "TUPRS");

    @FindBy(xpath = "//ul[@class='sbnBtf']//div[@class='COaKTb']")
    private List<WebElement> actualStockSymbols;

    public GoogleFinancePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // New method to retrieve the page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    public List<String> getExpectedStockSymbols() {
        return expectedStockSymbols;
    }

    public List<String> getActualStockSymbols() {
        return actualStockSymbols.stream()
                .map(WebElement::getText)
                .toList();
    }

    private List<String> expectedNegativeStockSymbols = Arrays.asList("GUBRF", "SISE", "TUPRS");

    @FindBy(xpath = "//ul[@class='sbnBtf']//li[.//span[contains(@class, 'Ebnabc') and contains(., '-')]]//div[@class='COaKTb']")
    private List<WebElement> acctualNegativeStockSymbols;

    public List<String> getExpectedNegativeStockSymbols() {
        return expectedNegativeStockSymbols;
    }

    public List<String> getActualNegativeStockSymbols() {
        return acctualNegativeStockSymbols.stream()
                .map(WebElement::getText)
                .toList();
    }

    @FindBy(xpath = "//ul[@class='sbnBtf']//li[.//span[contains(@class, 'P2Luy Ebnabc') and contains(., '-')]]//div[@class='YMlKec']")
    private List<WebElement> priceOfActualNegativeStockSymbols;

    public List<Double> getPricesOfActualNegativeStockSymbols() {
        return priceOfActualNegativeStockSymbols.stream()
                .map(WebElement::getText)
                .map(p -> p.replaceAll("[^\\d.]", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    public Map<String, Double> getNegativeStockSymbolsWithPrices() {
        Map<String, Double> negativeStocksWithPrices = new HashMap<>();
        List<String> negSymbols = acctualNegativeStockSymbols.stream()
                .map(WebElement::getText)
                .toList();

        List<Double> negStockPrices = priceOfActualNegativeStockSymbols.stream()
                .map(WebElement::getText)
                .map(p -> p.replaceAll("[^\\d.]", ""))
                .map(Double::parseDouble)
                .toList();

        for (int i = 0; i < negSymbols.size(); i++) {
            negativeStocksWithPrices.put(negSymbols.get(i), negStockPrices.get(i));
        }

        return negativeStocksWithPrices;
    }

    @FindBy(xpath = "//ul[@class='sbnBtf']//li[.//span[contains(@class,'Ez2Ioe') and contains(.,'+')]]//div[@class='COaKTb']")
    private List<WebElement> positiveStockSymbols;

    @FindBy(xpath = "//ul[@class='sbnBtf']//li[.//span[contains(@class,'P2Luy Ez2Ioe') and contains(.,'+')]]//div[@class='YMlKec']")
    private List<WebElement> positiveStockPrices;

    public Map<String, Double> getPositiveStockSymbolsWithPrices() {
        Map<String, Double> positiveStocksWithPrices = new HashMap<>();

        List<String> symbols = positiveStockSymbols.stream()
                .map(WebElement::getText)
                .toList();

        List<Double> prices = positiveStockPrices.stream()
                .map(WebElement::getText)
                .map(p -> p.replaceAll("[^\\d.]", ""))
                .map(Double::parseDouble)
                .toList();

        for (int i = 0; i < symbols.size(); i++) {
            positiveStocksWithPrices.put(symbols.get(i), prices.get(i));
        }

        return positiveStocksWithPrices;
    }

    @FindBy(xpath = "//ul[@class='sbnBtf']//div[@class='ZvmM7']")
    private List<WebElement> listOfStockDescriptions;

    public List<String> getStockDescriptions() {
        return listOfStockDescriptions.stream()
                .map(WebElement::getText)
                .toList();
    }
}
