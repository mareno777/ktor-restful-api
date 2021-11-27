package id.mareno.data

import id.mareno.data.entity.Customer
import id.mareno.data.model.CreateCustomerRequest
import id.mareno.data.model.CustomerResponse
import id.mareno.helper.toCustomerResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

class CustomerRepositoryImpl(private val database: Database) : CustomerRepository {

    override suspend fun getAllCustomers(): List<CustomerResponse> {
        val launchResult = suspendedTransactionAsync(Dispatchers.IO, database) {
            Customer.selectAll().map { resultRow ->
                resultRow.toCustomerResponse()
            }
        }
        return launchResult.await()
    }

    override suspend fun getCustomer(id: String): CustomerResponse? {
        val launchResult = suspendedTransactionAsync(Dispatchers.IO, database) {
            Customer.select { Customer.id eq id }.singleOrNull()?.toCustomerResponse()
        }
        return launchResult.await()
    }

    override suspend fun insert(createCustomerRequest: CreateCustomerRequest): CustomerResponse {
        suspendedTransactionAsync {
            Customer.insert {
                createCustomerRequest.also { customer ->
                    it[id] = customer.id
                    it[firstName] = customer.firstName
                    it[lastName] = customer.lastName
                    it[email] = customer.email
                }
            }
        }.join()
        return CustomerResponse(
            id = createCustomerRequest.id,
            firstName = createCustomerRequest.firstName,
            lastName = createCustomerRequest.lastName,
            email = createCustomerRequest.email,
        )
    }

    override suspend fun update(id: String): CustomerResponse {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String): CustomerResponse {
        TODO("Not yet implemented")
    }
}