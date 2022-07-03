package de.tschuehly.exchangerateservice.service

import de.tschuehly.exchangerateservice.bo.CurrencyDetail
import de.tschuehly.exchangerateservice.bo.CurrencyRateDetail
import de.tschuehly.exchangerateservice.error.CurrencyNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

@Service
class CurrencyService {
    // this would be saved in DB
    var currencyDetailList = mutableSetOf(CurrencyDetail("EUR",0))

    fun getExchangeRate(baseCurrency: String, targetCurrency: String): CurrencyRateDetail {
        val exchangeRates = getEcbExchangeRateData()
        var currencyRateDetail = if (baseCurrency == "EUR") {
            getCurrencyRateDetail(exchangeRates, targetCurrency)
        } else {
            val baseCurrencyDetail = getCurrencyRateDetail(exchangeRates, baseCurrency)
            val targetCurrencyDetail = getCurrencyRateDetail(exchangeRates, targetCurrency)
            CurrencyRateDetail(baseCurrency,
                    targetCurrency,
                    targetCurrencyDetail.rate / baseCurrencyDetail.rate)
        }

        // only targetcurrency is currently tracked
        currencyDetailList.map {
            if(it.identifier == targetCurrency){
                it.timesRequested = it.timesRequested + 1
            }else{
                it
        }
        }
        return currencyRateDetail
    }



    private fun getCurrencyRateDetail(exchangeRates: List<CurrencyRateDetail>, targetCurrency: String) =
            exchangeRates.firstOrNull {
                it.targetCurrency == targetCurrency
            } ?: throw CurrencyNotFoundException("Could not find currency: $targetCurrency")

    private fun getEcbExchangeRateData(): List<CurrencyRateDetail> {
        val restTemplate = RestTemplate()
        val ecbResponse = restTemplate.getForObject<String>("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml?d77b9d32811c6036126e2d3d784a0ee0")
        val xmlDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(InputSource(StringReader(ecbResponse)))
        val nodeList = xmlDocument.getElementsByTagName("Cube")
        val exchangeRates = mutableListOf(CurrencyRateDetail("EUR","EUR",1.0))
        for (i in 0 until nodeList.length) {
            if (nodeList.item(i).attributes.getNamedItem("currency") != null) {
                val currencyIdentifier = nodeList.item(i).attributes.getNamedItem("currency").nodeValue
                val rate = nodeList.item(i).attributes.getNamedItem("rate").nodeValue.toDouble()
                exchangeRates.add(CurrencyRateDetail("EUR", currencyIdentifier, rate))
                // Here instead of just adding it to a list you would check if its another day and save it into the db if its a new date
                currencyDetailList.add(CurrencyDetail(currencyIdentifier))
            }
        }
        return exchangeRates.toList()
    }

    fun getSupportedCurrencies(): MutableSet<CurrencyDetail> {
        getEcbExchangeRateData()
        return currencyDetailList
    }

    fun convertCurrency(amount: Double, baseCurrency: String, targetCurrency: String): Pair<CurrencyRateDetail,Double> {
        val currencyRateDetail = getExchangeRate(baseCurrency, targetCurrency)
        return Pair(currencyRateDetail,(amount * currencyRateDetail.rate))
    }


}