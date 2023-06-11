package io.vitamax.singleschematenantdemo.domain.store.dto

import java.util.*

data class CreateStoreDto(
    val name: String,
    val tenantId: UUID = UUID.randomUUID(),
)
