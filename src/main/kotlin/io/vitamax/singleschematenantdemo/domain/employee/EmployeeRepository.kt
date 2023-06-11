package io.vitamax.singleschematenantdemo.domain.employee

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface EmployeeRepository: JpaRepository<EmployeeEntity, UUID> {
    fun findAllByStoreIdIn(storeIds: List<UUID>): List<EmployeeEntity>
    fun findAllByStoreId(storeId: UUID): List<EmployeeEntity>
}
