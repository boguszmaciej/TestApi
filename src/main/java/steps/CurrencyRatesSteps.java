package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class CurrencyRatesSteps {

    private Response response;

    @Given("API NBP is accessible")
    public void api_nbp_is_accessible() {
        RestAssured.baseURI = "http://api.nbp.pl";
    }

    @When("Request the current exchange rates from table A")
    public void request_the_current_exchange_rates_from_table_a() {
        response = RestAssured.get("/api/exchangerates/tables/A?format=json");
        int statusCode = response.getStatusCode();
        System.out.println("Response status code: " + statusCode);
        Assert.assertEquals(200, statusCode);
    }

    @Then("Display the exchange rate for currency with code {string}")
    public void display_the_exchange_rate_for_currency_with_code(String currencyCode) {
        List<List<Map<String, Object>>> currencyRates = response.jsonPath().getList("rates");

        for (List<Map<String, Object>> rates : currencyRates) {
            for (Map<String, Object> rate : rates) {
                if (rate.get("code").equals(currencyCode)) {
                    System.out.println("\nExchange rate for " + currencyCode + ": " + rate.get("mid"));
                }
            }
        }
    }

    @Then("Display the exchange rate for currency with name {string}")
    public void display_the_exchange_rate_for_currency_with_name(String currencyName) {
        List<List<Map<String, Object>>> currencyRates = response.jsonPath().getList("rates");

        for (List<Map<String, Object>> rates : currencyRates) {
            for (Map<String, Object> rate : rates) {
                if (rate.get("currency").equals(currencyName)) {
                    System.out.println("Exchange rate for " + currencyName + ": " + rate.get("mid"));
                }
            }
        }
    }

    @Then("Display currencies with rates above {double}")
    public void display_currencies_with_rates_above(double rateValue) {
        List<List<Map<String, Object>>> currencyRates = response.jsonPath().getList("rates");

        System.out.printf("\nCurrencies with rates above: %s\n", rateValue);

        for (List<Map<String, Object>> rates : currencyRates) {
            for (Map<String, Object> rate : rates) {
                double mid = Double.parseDouble(rate.get("mid").toString());
                if (mid > rateValue) {
                    System.out.println(rate.get("currency") + ": " + rate.get("mid"));
                }
            }
        }
    }

    @Then("Display currencies with rates below {double}")
    public void display_currencies_with_rates_below(double rateValue) {
        List<List<Map<String, Object>>> currencyRates = response.jsonPath().getList("rates");
        System.out.printf("\nCurrencies with rates below: %s\n", rateValue);

        for (List<Map<String, Object>> rates : currencyRates) {
            for (Map<String, Object> rate : rates) {
                double mid = Double.parseDouble(rate.get("mid").toString());
                if (mid < rateValue) {
                    System.out.println(rate.get("currency") + ": " + String.format("%.6f", rate.get("mid")));
                }
            }
        }
    }
}
