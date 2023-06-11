package io.vitamax.singleschematenantdemo.domain.employee

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "employee")
class EmployeeEntity (

    @Column
    val name: String = "",

    @Column
    val storeId: UUID? = null,
) {
    @Id
    val id: UUID = UUID.randomUUID()

    fun toDomain() = Employee(
        id = id,
        name = name,
        storeId = storeId,
    )
}

fun List<EmployeeEntity>.toDomains()
    = this.map { it.toDomain() }
