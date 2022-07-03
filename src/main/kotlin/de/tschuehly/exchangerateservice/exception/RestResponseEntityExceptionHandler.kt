package de.tschuehly.exchangerateservice.exception

import de.tschuehly.exchangerateservice.error.CurrencyNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class RestResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(CurrencyNotFoundException::class)
    fun handleIllegalArgumentException(exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(HttpStatus.BAD_REQUEST, exception.localizedMessage), HttpStatus.BAD_REQUEST)
    }
}
class ErrorResponse(private val status: HttpStatus, val error: String) {
    val timestamp: Date = Date()
    val code: Number = status.value()
}
