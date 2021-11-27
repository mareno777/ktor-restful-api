package id.mareno.routes

import id.mareno.data.CustomerRepository
import id.mareno.data.model.CreateCustomerRequest
import id.mareno.data.model.WebResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.customerRouting() {
    val customerRepository by inject<CustomerRepository>()

    route("/customer") {
        get {
            val response = customerRepository.getAllCustomers()
            if (response.isNotEmpty()) {
                val webResponse = WebResponse(
                    code = HttpStatusCode.OK.value,
                    status = HttpStatusCode.OK.description,
                    data = response
                )
                call.respond(HttpStatusCode.OK, webResponse)
            } else {
                val httpStatusCode = HttpStatusCode.NotFound
                val webResponse = WebResponse(
                    code = httpStatusCode.value,
                    status = httpStatusCode.description,
                    data = response
                )
                call.respond(httpStatusCode, webResponse)
            }
        }
        get("{id}") {
            val response = customerRepository.getCustomer(call.parameters["id"]!!)
            if (response != null) {
                val webResponse = WebResponse(
                    code = HttpStatusCode.OK.value,
                    status = HttpStatusCode.OK.description,
                    data = response
                )
                call.respond(HttpStatusCode.OK, webResponse)
            } else {
                val httpStatusCode = HttpStatusCode.NotFound
                val webResponse = WebResponse(
                    code = httpStatusCode.value,
                    status = httpStatusCode.description,
                    data = "Customer not found"
                )
                call.respond(httpStatusCode, webResponse)
            }
        }
        post {
            val createCustomerRequest = call.receive<CreateCustomerRequest>()
            val response = customerRepository.insert(createCustomerRequest)
            val webResponse = WebResponse(
                code = HttpStatusCode.Created.value,
                status = HttpStatusCode.Created.description,
                data = response
            )
            call.respond(HttpStatusCode.Created, webResponse)
        }
        delete("{id}") {

        }
    }
}