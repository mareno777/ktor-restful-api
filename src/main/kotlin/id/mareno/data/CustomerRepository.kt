package id.mareno.data

import id.mareno.data.model.CreateCustomerRequest
import id.mareno.data.model.CustomerResponse

interface CustomerRepository {

    suspend fun getAllCustomers(): List<CustomerResponse>

    suspend fun getCustomer(id: String): CustomerResponse?

    suspend fun insert(createCustomerRequest: CreateCustomerRequest): CustomerResponse

    suspend fun update(id: String): CustomerResponse

    suspend fun delete(id: String): CustomerResponse
}