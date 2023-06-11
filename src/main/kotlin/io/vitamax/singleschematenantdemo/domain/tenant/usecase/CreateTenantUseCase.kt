package io.vitamax.singleschematenantdemo.domain.tenant.usecase

import io.vitamax.singleschematenantdemo.common.PreconditionFailedException
import io.vitamax.singleschematenantdemo.domain.tenant.Tenant
import io.vitamax.singleschematenantdemo.domain.tenant.TenantEntity
import io.vitamax.singleschematenantdemo.domain.tenant.TenantRepository
import io.vitamax.singleschematenantdemo.domain.tenant.dto.CreateTenantDto
import org.springframework.stereotype.Component

@Component
class CreateTenantUseCase(
    private val repo: TenantRepository
) {
    fun execute(dto: CreateTenantDto): Tenant = with(dto) {
        val tenant = TenantEntity(
            name = name,
            isPrivileged = isPrivileged
        )
        return try {
            repo.save(tenant).toDomain()
        } catch (e: Exception) {
            throw PreconditionFailedException(e.message ?: e.localizedMessage)
        }
    }
}
