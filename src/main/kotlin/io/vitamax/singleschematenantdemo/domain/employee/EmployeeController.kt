package io.vitamax.singleschematenantdemo.domain.employee

import io.vitamax.singleschematenantdemo.domain.employee.dto.CreateEmployeeDto
import io.vitamax.singleschematenantdemo.domain.employee.usecase.CreateEmployeeUseCase
import io.vitamax.singleschematenantdemo.domain.employee.usecase.FindEmployeeUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/employees")
class EmployeeController(
    private val findUseCase: FindEmployeeUseCase,
    private val createUseCase: CreateEmployeeUseCase,
) {

    @GetMapping("/{id}")
    fun findAll(@PathVariable id: UUID): ResponseEntity<Employee> {
        return ResponseEntity.ok(findUseCase.findById(id).toDomain())
    }

    @PostMapping("")
    fun create(
        @RequestBody dto: CreateEmployeeDto,
    ): ResponseEntity<Employee> {
        return ResponseEntity.ok(createUseCase.execute(dto).toDomain())
    }
}
