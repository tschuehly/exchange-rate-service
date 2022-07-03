package de.tschuehly.exchangerateservice.dto

import de.tschuehly.exchangerateservice.bo.CurrencyDetail

class CurrencyDetailDTO (
        var identifier: String,
        var timesRequested: Int = 0
){
    constructor(currencyDetail: CurrencyDetail): this(
            currencyDetail.identifier,
            currencyDetail.timesRequested
    )
}