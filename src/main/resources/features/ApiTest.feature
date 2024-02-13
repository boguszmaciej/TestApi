Feature: Zadanie Testowe

  Scenario: Currency Rates
    Given API NBP is accessible
    When I request the current exchange rates from table A
    Then I display the exchange rate for currency with code "USD"
    Then I display the exchange rate for currency with name "dolar amerykański"
    Then I display currencies with rates above 5
    Then I display currencies with rates below 3
