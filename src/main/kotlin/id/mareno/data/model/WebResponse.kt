package id.mareno.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WebResponse<T>(
    val code: Int,
    val status: String,
    val data: T
)
