package io.vitamax.singleschematenantdemo.domain.employee

import java.util.*

data class Employee(
    val id: UUID,
    val name: String,
    val storeId: UUID?,
)
