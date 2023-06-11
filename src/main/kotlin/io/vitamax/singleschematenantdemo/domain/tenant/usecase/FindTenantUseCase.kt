package io.vitamax.singleschematenantdemo.domain.tenant.usecase

import io.vitamax.singleschematenantdemo.domain.tenant.Tenant
import io.vitamax.singleschematenantdemo.domain.tenant.TenantNotFoundException
import io.vitamax.singleschematenantdemo.domain.tenant.TenantRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class FindTenantUseCase(
    private val repo: TenantRepository,
) {

    fun findById(id: UUID): Tenant {
        return repo.findByIdOrNull(id)?.toDomain() ?: throw TenantNotFoundException(id = id)
    }

    fun findAll(): List<Tenant> {
        return repo.findAll().map { it.toDomain() }
    }
}
