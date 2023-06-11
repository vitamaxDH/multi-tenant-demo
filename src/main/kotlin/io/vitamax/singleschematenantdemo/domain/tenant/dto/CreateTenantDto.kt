package io.vitamax.singleschematenantdemo.domain.tenant.dto

data class CreateTenantDto(
    val name: String,
    val isPrivileged: Boolean = false,
)
