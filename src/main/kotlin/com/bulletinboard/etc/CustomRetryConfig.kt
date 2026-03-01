package com.bulletinboard.etc

import org.springframework.core.retry.RetryPolicy
import org.springframework.core.retry.RetryTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.ErrorResponseException
import org.springframework.web.server.ResponseStatusException
import java.io.IOException

@Service
class CustomRetryConfig {
    fun a(): RetryTemplate {
        val retryPolicy: RetryPolicy =
            RetryPolicy
                .builder()
                .includes(ResponseStatusException::class.java)
                .maxRetries(3)
                .multiplier(1.0)
                .build()

        val retryTemplate = RetryTemplate(retryPolicy)
        retryTemplate.setRetryListener(CustomRetryListener())
        return retryTemplate
    }

    fun test2(): RetryTemplate {
        val retryPolicy: RetryPolicy =
            RetryPolicy
                .builder()
                .predicate {
                    when {
                        it !is ResponseStatusException -> false
                        it.statusCode == HttpStatus.NOT_FOUND || it.statusCode == HttpStatus.BAD_REQUEST -> true
                        else -> false
                    }
                }
//                .predicate({
//                    if (it !is ResponseStatusException) {
//                        return@predicate false
//                    } else if (it.statusCode == HttpStatus.NOT_FOUND || it.statusCode == HttpStatus.BAD_REQUEST) {
//                        return@predicate true
//                    }
//                    return@predicate false
//                })
                .build()
        val retryTemplate = RetryTemplate(retryPolicy)
        retryTemplate.setRetryListener(CustomRetryListener())
        return retryTemplate
    }

    fun isPredicate(): Boolean {
        return true
    }
}
