Feature: Zadanie Testowe

  Scenario: Currency Rates
    Given API NBP is accessible
    When Request the current exchange rates from table A
    Then Display the exchange rate for currency with code "USD"
    Then Display the exchange rate for currency with name "dolar amerykański"
    Then Display currencies with rates above 5
    Then Display currencies with rates below 3
