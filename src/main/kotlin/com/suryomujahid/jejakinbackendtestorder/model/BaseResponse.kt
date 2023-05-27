package com.suryomujahid.jejakinbackendtestorder.model

data class BaseResponse(
    val status: String,
    val message: String,
    val data: Any?
)