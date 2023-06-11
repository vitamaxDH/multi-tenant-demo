package io.vitamax.singleschematenantdemo.domain.employee.usecase

import io.vitamax.singleschematenantdemo.domain.employee.EmployeeEntity
import io.vitamax.singleschematenantdemo.domain.employee.EmployeeRepository
import io.vitamax.singleschematenantdemo.domain.employee.dto.CreateEmployeeDto
import io.vitamax.singleschematenantdemo.domain.store.usecase.FindStoreUseCase
import org.springframework.stereotype.Component

@Component
class CreateEmployeeUseCase(
    private val repo: EmployeeRepository,
    private val findStoreUseCase: FindStoreUseCase,
) {

    fun execute(dto: CreateEmployeeDto): EmployeeEntity {
        val store = findStoreUseCase.findById(dto.storeId)
        val employee = EmployeeEntity(
            name = dto.name,
            storeId = store.id,
        )
        return repo.save(employee)
    }
}
