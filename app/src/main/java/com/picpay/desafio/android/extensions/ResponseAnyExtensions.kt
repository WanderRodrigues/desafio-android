package com.picpay.desafio.android.extensions

import com.picpay.desafio.android.service.ResponseAny
import com.picpay.desafio.android.service.ResponseEmpty
import com.picpay.desafio.android.service.ResponseError
import com.picpay.desafio.android.service.ResponseSuccess

fun <T> ResponseAny<T>.read(
    success: (T) -> Unit,
    error: ((Exception) -> Unit)? = null,
    empty: ((Int) -> Unit)? = null
) {
    when (this) {
        is ResponseSuccess -> success(this.body)
        is ResponseError -> error?.invoke(this.exception)
        is ResponseEmpty -> empty?.invoke(this.code)
    }
}