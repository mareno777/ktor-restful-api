package id.mareno.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCustomerRequest(
    val firstName: String,
    val lastName: String,
    val email: String
)
