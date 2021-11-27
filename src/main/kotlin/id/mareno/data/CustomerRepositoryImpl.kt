package id.mareno.data

import id.mareno.data.entity.Customer
import id.mareno.data.model.CreateCustomerRequest
import id.mareno.data.model.CustomerResponse
import id.mareno.data.model.UpdateCustomerRequest
import id.mareno.helper.isNotExist
import id.mareno.helper.toCustomerResponse
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
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
            val query = Customer.select { Customer.id eq id }
            if (query.isNotExist()) {
                null
            } else {
                query.single().toCustomerResponse()
            }
        }
        return launchResult.await()
    }

    override suspend fun insert(createCustomerRequest: CreateCustomerRequest): CustomerResponse {
        newSuspendedTransaction(Dispatchers.IO) {
            Customer.insert {
                createCustomerRequest.also { customer ->
                    it[id] = customer.id
                    it[firstName] = customer.firstName
                    it[lastName] = customer.lastName
                    it[email] = customer.email
                }
            }
        }
        return CustomerResponse(
            id = createCustomerRequest.id,
            firstName = createCustomerRequest.firstName,
            lastName = createCustomerRequest.lastName,
            email = createCustomerRequest.email,
        )
    }

    override suspend fun update(id: String, updateCustomerRequest: UpdateCustomerRequest): CustomerResponse {
        newSuspendedTransaction(Dispatchers.IO) {
            if (Customer.select { Customer.id eq id }.isNotExist()) {
                throw NoSuchElementException("Data doesn't exist")
            }
            Customer.update({ Customer.id eq id }) {
                updateCustomerRequest.also { customer ->
                    it[firstName] = customer.firstName
                    it[lastName] = customer.lastName
                    it[email] = customer.email
                }
            }
        }
        return CustomerResponse(
            id = id,
            firstName = updateCustomerRequest.firstName,
            lastName = updateCustomerRequest.lastName,
            email = updateCustomerRequest.email,
        )
    }

    override suspend fun delete(id: String) {
        newSuspendedTransaction(Dispatchers.IO) {
            if (Customer.select { Customer.id eq id }.isNotExist()) {
                throw NoSuchElementException("Data doesn't exist")
            }
            Customer.deleteWhere {
                Customer.id eq id
            }
        }
    }
}