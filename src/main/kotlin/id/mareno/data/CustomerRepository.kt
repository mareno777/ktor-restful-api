package id.mareno.data

import id.mareno.data.model.CreateCustomerRequest
import id.mareno.data.model.CustomerResponse
import id.mareno.data.model.UpdateCustomerRequest

interface CustomerRepository {

    suspend fun getAllCustomers(): List<CustomerResponse>

    suspend fun getCustomer(id: String): CustomerResponse?

    suspend fun insert(createCustomerRequest: CreateCustomerRequest): CustomerResponse

    suspend fun update(id: String, updateCustomerRequest: UpdateCustomerRequest): CustomerResponse

    suspend fun delete(id: String)
}