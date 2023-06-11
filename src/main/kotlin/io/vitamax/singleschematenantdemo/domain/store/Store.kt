package io.vitamax.singleschematenantdemo.domain.store

import io.vitamax.singleschematenantdemo.domain.employee.Employee
import java.util.*

data class Store(
    val id: UUID,
    val name: String,
    val employees: List<Employee> = emptyList(),
    val tenantId: UUID,
)
