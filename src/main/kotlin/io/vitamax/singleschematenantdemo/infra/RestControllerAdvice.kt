package io.vitamax.singleschematenantdemo.infra

import io.vitamax.singleschematenantdemo.common.NotFoundException
import io.vitamax.singleschematenantdemo.common.PreconditionFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class RestControllerAdvice {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(object {
                val message = e.message
            })
    }

    @ExceptionHandler(PreconditionFailedException::class)
    fun handlePreconditionFailed(e: PreconditionFailedException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
            .body(object {
                val message = e.message
            })
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodValidation(ex: MethodArgumentNotValidException): ResponseEntity<String> {
        val errors = ex.bindingResult
            .fieldErrors
            .map { error -> "${error.field}: ${error.defaultMessage}" }

        return ResponseEntity(errors.toString(), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(ex: Exception): ResponseEntity<String> {
        return ResponseEntity.internalServerError()
            .body(ex.message ?: ex.localizedMessage)
    }
}
