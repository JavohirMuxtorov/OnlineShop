package com.example.savdo.model

data class BaseResponse<T>(
    val success: Boolean=true,
    val data: T,
    val message: String,
    val error_code: Int
)
