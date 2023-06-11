package io.vitamax.singleschematenantdemo.domain.employee.dto

import java.util.*

data class CreateEmployeeDto(
    val name: String,
    val storeId: UUID,
)
