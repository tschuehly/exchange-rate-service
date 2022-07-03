package de.tschuehly.exchangerateservice.controller

import de.tschuehly.exchangerateservice.bo.CurrencyDetail
import de.tschuehly.exchangerateservice.dto.CurrencyConversionDTO
import de.tschuehly.exchangerateservice.dto.CurrencyDetailDTO
import de.tschuehly.exchangerateservice.dto.CurrencyRateDetailDTO
import de.tschuehly.exchangerateservice.dto.LinkDTO
import de.tschuehly.exchangerateservice.service.CurrencyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/currencies")
class CurrencyController(val currencyService: CurrencyService) {
    @GetMapping("/rate")
    fun getRate(
            @RequestParam baseCurrency: String,
            @RequestParam targetCurrency: String
        ): CurrencyRateDetailDTO {
        return CurrencyRateDetailDTO(
                currencyService.getExchangeRate(baseCurrency,targetCurrency)
        )

    }
    @GetMapping("")
    fun getSupportedCurrencies(): List<CurrencyDetailDTO> {
        return currencyService.getSupportedCurrencies().map { CurrencyDetailDTO(it) }
    }
    @GetMapping("/convert")
    fun convertCurrency(
            @RequestParam amount: Double,
            @RequestParam baseCurrency: String,
            @RequestParam targetCurrency: String
    ): CurrencyConversionDTO {
        val (currencyRateDetail, convertedAmount) = currencyService.convertCurrency(amount,baseCurrency,targetCurrency)
        return CurrencyConversionDTO(currencyRateDetail,amount,convertedAmount)
    }

    @GetMapping("/chart")
    fun getChartLink(
            @RequestParam baseCurrency: String,
            @RequestParam targetCurrency: String
    ): LinkDTO {
        return  LinkDTO("https://www.xe.com/currencycharts/?from=$baseCurrency&to=$targetCurrency")
    }
}