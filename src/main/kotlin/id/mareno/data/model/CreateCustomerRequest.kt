package id.mareno.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateCustomerRequest(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)
