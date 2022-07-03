package de.tschuehly.exchangerateservice.dto

import de.tschuehly.exchangerateservice.bo.CurrencyRateDetail

open class CurrencyRateDetailDTO(var baseCurrency: String,
                                 var targetCurrency: String,
                                 var rate: Double = 0.0){
    constructor(currencyRateDetail: CurrencyRateDetail): this(
            currencyRateDetail.baseCurrency,
            currencyRateDetail.targetCurrency,
            currencyRateDetail.rate
    )
}
