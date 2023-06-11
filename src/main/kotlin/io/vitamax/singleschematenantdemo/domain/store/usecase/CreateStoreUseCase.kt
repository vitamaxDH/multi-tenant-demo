package io.vitamax.singleschematenantdemo.domain.store.usecase

import io.vitamax.singleschematenantdemo.domain.store.dto.CreateStoreDto
import io.vitamax.singleschematenantdemo.domain.store.StoreEntity
import io.vitamax.singleschematenantdemo.domain.store.StoreRepository
import io.vitamax.singleschematenantdemo.domain.tenant.usecase.FindTenantUseCase
import org.springframework.stereotype.Component

@Component
class CreateStoreUseCase(
    private val repo: StoreRepository,
    private val findTenantUseCase: FindTenantUseCase,
) {

    fun execute(dto: CreateStoreDto): StoreEntity {
        val store = StoreEntity(
            name = dto.name,
        ).apply {
            tenantId = dto.tenantId
        }
        return repo.save(store)
    }
}
