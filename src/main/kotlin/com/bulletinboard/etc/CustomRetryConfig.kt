package com.bulletinboard.etc

import org.springframework.core.retry.RetryPolicy
import org.springframework.core.retry.RetryTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.function.Predicate

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

    // ラムダ式でpredicateを定義 ifかwhenかはお好みか
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

    // メソッドでPredicateを定義して代入
    fun isPredicate(): Predicate<Throwable> {
        val a: Predicate<Throwable> =
            object : Predicate<Throwable> {
                override fun test(t: Throwable): Boolean {
                    if (t !is ResponseStatusException) {
                        return false
                    } else if (t.statusCode == HttpStatus.BAD_REQUEST || t.statusCode == HttpStatus.NOT_FOUND) {
                        return true
                    }
                    return false
                }
            }
        return a
    }

    fun test3(): RetryTemplate {
        val retryPolicy: RetryPolicy =
            RetryPolicy
                .builder()
                .predicate(isPredicate())
                .build()
        val retryTemplate = RetryTemplate(retryPolicy)
        retryTemplate.setRetryListener(CustomRetryListener())
        return retryTemplate
    }

    // 変数でPredicateを宣言して代入
    val a =
        object : Predicate<Throwable> {
            override fun test(t: Throwable): Boolean {
                if (t !is ResponseStatusException) {
                    return false
                } else if (t.statusCode == HttpStatus.BAD_REQUEST || t.statusCode == HttpStatus.NOT_FOUND) {
                    return true
                }
                return false
            }
        }

    fun test4(): RetryTemplate {
        val retryPolicy: RetryPolicy =
            RetryPolicy
                .builder()
                .predicate(a)
                .build()
        val retryTemplate = RetryTemplate(retryPolicy)
        retryTemplate.setRetryListener(CustomRetryListener())
        return retryTemplate
    }

    // includesで再試行対象の例外を指定する。Predicateで例外の種類を確認するが、Gemini曰くこちらの方がパフォーマンスが良いとのこと
    // これではRuntimeExceptionの時再実行しない。。。
    val b =
        object : Predicate<Throwable> {
            override fun test(t: Throwable): Boolean {
                if (t !is ResponseStatusException) {
                    return false
                } else if (t.statusCode == HttpStatus.BAD_REQUEST || t.statusCode == HttpStatus.NOT_FOUND) {
                    return true
                }
                return false
            }
        }

    fun test5(): RetryTemplate {
        val retryPolicy: RetryPolicy =
            RetryPolicy
                .builder()
                .includes(RuntimeException::class.java)
                .predicate(b)
                .build()
        val retryTemplate = RetryTemplate(retryPolicy)
        retryTemplate.setRetryListener(CustomRetryListener())
        return retryTemplate
    }

    // 特定の例外をピンポイントで確認するには「::class.java」で確認できる。これでRuntimeException単体で見て、サブクラスはリトライ対象外とできる
    val c =
        object : Predicate<Throwable> {
            override fun test(t: Throwable): Boolean {
                if (t::class.java == RuntimeException::class.java) {
                    return true
                } else if (t is ResponseStatusException &&
                    (t.statusCode == HttpStatus.BAD_REQUEST || t.statusCode == HttpStatus.NOT_FOUND)
                ) {
                    return true
                }
                return false
            }
        }

    fun test6(): RetryTemplate {
        val retryPolicy: RetryPolicy =
            RetryPolicy
                .builder()
                .includes(RuntimeException::class.java)
//                .predicate(c)
                .build()
        val retryTemplate = RetryTemplate(retryPolicy)
        retryTemplate.setRetryListener(CustomRetryListener())
        return retryTemplate
    }
}
