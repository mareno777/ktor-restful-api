package id.mareno.data.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Customer : Table("customers") {
    val id: Column<String> = varchar("id", 7)
    val firstName: Column<String> = varchar("first_name", 24)
    val lastName: Column<String> = varchar("last_name", 24)
    val email: Column<String> = varchar("email", 40).uniqueIndex("index_email")

    override val primaryKey = PrimaryKey(id, name = "pk_customers_id")
}