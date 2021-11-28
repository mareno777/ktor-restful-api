package id.mareno.plugins

import id.mareno.data.model.WebResponse
import id.mareno.routes.customerRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun Application.configureRouting() {
    routing {
        customerRouting()
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
        install(StatusPages) {
            exception<AuthenticationException> { cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { cause ->
                call.respond(HttpStatusCode.Forbidden)
            }
            exception<NoSuchElementException> { cause ->
                val httpStatusCode = HttpStatusCode.MethodNotAllowed
                call.respond(
                    httpStatusCode, WebResponse(
                        code = httpStatusCode.value,
                        status = httpStatusCode.description,
                        data = cause.message
                    )
                )
            }
            exception<ExposedSQLException> { cause ->
                val httpStatusCode = HttpStatusCode.MethodNotAllowed
                call.respond(
                    httpStatusCode, WebResponse(
                        code = httpStatusCode.value,
                        status = httpStatusCode.description,
                        data = cause.message
                    )
                )
            }
            exception<IllegalArgumentException> { cause ->
                val httpStatusCode = HttpStatusCode.Unauthorized
                call.respond(
                    httpStatusCode, WebResponse(
                        code = httpStatusCode.value,
                        status = httpStatusCode.description,
                        data = cause.message
                    )
                )
            }
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
