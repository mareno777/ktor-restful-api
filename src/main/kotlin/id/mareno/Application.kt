package id.mareno

import id.mareno.di.mainModule
import id.mareno.plugins.configureMonitoring
import id.mareno.plugins.configureRouting
import id.mareno.plugins.configureSerialization
import io.ktor.application.*
import io.ktor.features.*
import org.koin.ktor.ext.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(DefaultHeaders)
    install(Koin) {
        modules(mainModule)
    }
    configureRouting()
    configureMonitoring()
    configureSerialization()
}
