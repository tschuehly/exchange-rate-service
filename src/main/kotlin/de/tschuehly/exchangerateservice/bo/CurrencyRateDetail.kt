package de.tschuehly.exchangerateservice.bo

data class CurrencyRateDetail(
        var baseCurrency: String,
        var targetCurrency: String,
        var rate: Double
)