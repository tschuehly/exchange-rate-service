package de.tschuehly.exchangerateservice.dto

import de.tschuehly.exchangerateservice.bo.CurrencyRateDetail

class CurrencyConversionDTO(
        var currencyRateDetail: CurrencyRateDetail,
        var baseAmount: Double,
        var convertedAmount: Double

)