package io.vitamax.singleschematenantdemo.domain.tenant

import java.util.*

data class Tenant(
    val id: UUID,
    val name: String,
    val isPrivileged: Boolean,
)
