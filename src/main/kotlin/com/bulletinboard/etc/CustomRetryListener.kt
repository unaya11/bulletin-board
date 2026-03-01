package com.bulletinboard.etc

import org.slf4j.LoggerFactory
import org.springframework.core.retry.RetryException
import org.springframework.core.retry.RetryListener
import org.springframework.core.retry.RetryPolicy
import org.springframework.core.retry.RetryState
import org.springframework.core.retry.Retryable

class CustomRetryListener : RetryListener {
    private val logger = LoggerFactory.getLogger(javaClass)

    // 試行回数（リトライ回数+1）を返します。
    fun getAttemptCount(retryState: RetryState): Int = retryState.retryCount + 1

    // 試行回数（リトライ回数+1）を返します。
    fun getAttemptCount(e: RetryException): Int = e.retryCount + 1

    // 毎試行の前に呼び出されます。
    override fun beforeRetry(
        retryPolicy: RetryPolicy,
        retryable: Retryable<*>,
    ) {
        logger.info("Restart")
    }

    // 成功の後に呼び出されます。
    override fun onRetrySuccess(
        retryPolicy: RetryPolicy,
        retryable: Retryable<*>,
        result: Any?,
    ) {
        logger.info("Restart Success. Return value = {}", result)
    }

    // 失敗した後に呼び出されます。
    override fun onRetryFailure(
        retryPolicy: RetryPolicy,
        retryable: Retryable<*>,
        throwable: Throwable,
    ) {
        logger.warn("Restart Failed. Exception = {}", throwable.message)
    }

    // 毎試行が完了した後に呼び出されます。
    override fun onRetryableExecution(
        retryPolicy: RetryPolicy,
        retryable: Retryable<*>,
        retryState: RetryState,
    ) {
        logger.info("Restart Completed. Times = {}, Success = {}", getAttemptCount(retryState), retryState.isSuccessful)
    }

    // 全試行が上限に達した場合に呼び出されます。
    override fun onRetryPolicyExhaustion(
        retryPolicy: RetryPolicy,
        retryable: Retryable<*>,
        exception: RetryException,
    ) {
        logger.info(
            "The maximum number of attempts has been reached, but the operation was unsuccessful. Times = {}, Exception = {}",
            getAttemptCount(exception),
            exception.message,
        )
    }
}
