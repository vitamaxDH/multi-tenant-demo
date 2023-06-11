package io.vitamax.singleschematenantdemo.domain.employee.usecase

import io.vitamax.singleschematenantdemo.domain.employee.EmployeeEntity
import io.vitamax.singleschematenantdemo.domain.employee.EmployeeNotFoundException
import io.vitamax.singleschematenantdemo.domain.employee.EmployeeRepository
import io.vitamax.singleschematenantdemo.infra.TenantFilter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@TenantFilter
@Component
class FindEmployeeUseCase(
    private val repo: EmployeeRepository,
) {
    fun findById(id: UUID): EmployeeEntity {
        return repo.findByIdOrNull(id) ?: throw EmployeeNotFoundException(id = id)
    }
}
