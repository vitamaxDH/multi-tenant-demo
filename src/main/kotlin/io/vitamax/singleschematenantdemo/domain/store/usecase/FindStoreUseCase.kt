package io.vitamax.singleschematenantdemo.domain.store.usecase

import io.vitamax.singleschematenantdemo.domain.employee.EmployeeRepository
import io.vitamax.singleschematenantdemo.domain.employee.toDomains
import io.vitamax.singleschematenantdemo.domain.store.Store
import io.vitamax.singleschematenantdemo.domain.store.StoreNotFoundException
import io.vitamax.singleschematenantdemo.domain.store.StoreRepository
import io.vitamax.singleschematenantdemo.infra.TenantFilter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@TenantFilter
@Component
class FindStoreUseCase(
    private val repo: StoreRepository,
    private val employeeRepo: EmployeeRepository,
) {

    fun findById(id: UUID): Store {
        val store = repo.findByIdOrNull(id) ?: throw StoreNotFoundException(id = id)
        val employees = employeeRepo.findAllByStoreId(store.id)
        return store.toDomain(employees.toDomains())
    }

    fun findAll(): List<Store> {
        val stores = repo.findList()
        val employees = employeeRepo.findAllByStoreIdIn(stores.map { it.id })
            .map { it.toDomain() }
        val employeesByStoreId = employees.groupBy { it.storeId }
        return stores.map { store ->
            store.toDomain(employeesByStoreId[store.id] ?: emptyList())
        }
    }
}
