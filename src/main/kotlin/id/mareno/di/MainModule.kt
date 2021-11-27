package id.mareno.di

import id.mareno.data.CustomerRepository
import id.mareno.data.CustomerRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val mainModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl(get()) }
    single {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/ktor_restful_api",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root", password = "root"
        )
    }

}