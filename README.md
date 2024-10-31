
# FinfareAutomation

## Project Description

The **FinfareAutomation** project is an automated test framework built with Java and Selenium for UI and API testing. This framework utilizes Page Object Model (POM) and Page Factory design patterns, making it modular, maintainable, and scalable. The tests verify and compare data retrieved from the UI with expected collections and include integration with GitHub Actions for continuous integration and nightly test runs.

This project was developed as a coding exercise to demonstrate automated testing skills, including writing tests, setting up GitHub Actions for automation, and organizing code effectively.

## Class Descriptions

### `ConfigManager`
This class is located in the `/config` package. It loads configuration properties from `config.properties`, such as `base.url` and other test-specific data.

### `DriverFactory`
Located in the `/factory` package, `DriverFactory` is responsible for managing WebDriver instances. It ensures the driver is initiated and properly closed after each test.

### `BasePage`
The `BasePage` class, found in the `/pages` package, serves as the base class for all page objects. It contains common methods used across different pages, like click and getText, with support for wait conditions.

### `GoogleFinancePage`
This page class models the Google Finance page and is also located in the `/pages` package. It uses the Page Factory pattern with `@FindBy` annotations to locate and interact with UI elements, including stock symbols, prices, and descriptions.

- `getExpectedStockSymbols()` - Returns the expected stock symbols.
- `getActualStockSymbols()` - Retrieves the stock symbols displayed on the page.
- `getNegativeStockSymbolsWithPrices()` - Gets stock symbols with negative values and their respective prices.
- `getPositiveStockSymbolsWithPrices()` - Gets stock symbols with positive values and their respective prices.

### `ReportManager`
In the `/utils` package, this class generates HTML test reports using ExtentReports, providing insights into test execution status.

### `GoogleFinanceTest`
The test class located in `/com/finfare/tests` contains automated tests for verifying stock symbols and their values on the Google Finance page. The tests include assertions to compare actual data retrieved from the UI with expected collections.

## GitHub Actions Workflows

This project includes GitHub Actions workflows for continuous integration:

1. **Manual Workflow** (`manual-workflow.yml`): Allows triggering specific test cases or the entire suite on demand.
2. **Nightly Workflow** (`nightly-workflow.yml`): Runs the full set of tests every night to ensure stability and catch regressions.

## How to Run the Tests

1. Clone the repository to your local machine.
2. Make sure you have JDK 22 and Maven installed.
3. Navigate to the project directory.
4. Run the tests with Maven:
   ```bash
   mvn clean test
   ```

## Configuration

To update test-specific configurations, edit the `config.properties` file under `/src/main/resources`.

**Example config.properties**:
```properties
base.url=https://www.google.com/finance
test.data=NFLX,MSFT,TSLA
```
