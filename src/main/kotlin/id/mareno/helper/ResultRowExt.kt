package id.mareno.helper

import id.mareno.data.entity.Customer
import id.mareno.data.model.CustomerResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCustomerResponse(): CustomerResponse {
    return CustomerResponse(
        id = this[Customer.id],
        firstName = this[Customer.firstName],
        lastName = this[Customer.lastName],
        email = this[Customer.email]
    )
}