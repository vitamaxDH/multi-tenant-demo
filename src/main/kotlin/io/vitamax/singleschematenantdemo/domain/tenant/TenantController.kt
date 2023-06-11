package io.vitamax.singleschematenantdemo.domain.tenant

import io.vitamax.singleschematenantdemo.domain.tenant.dto.CreateTenantDto
import io.vitamax.singleschematenantdemo.domain.tenant.usecase.CreateTenantUseCase
import io.vitamax.singleschematenantdemo.domain.tenant.usecase.FindTenantUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/tenants")
class TenantController(
    private val findUseCase: FindTenantUseCase,
    private val createUseCase: CreateTenantUseCase,
) {
    @GetMapping("")
    fun findAll(): ResponseEntity<Any> {
        val tenants = findUseCase.findAll()
        return ResponseEntity.ok(object {
            val data = tenants
        })
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Tenant> {
        return ResponseEntity.ok(findUseCase.findById(id))
    }

    @PostMapping("")
    fun create(
        @RequestBody dto: CreateTenantDto
    ): ResponseEntity<Tenant> {
        return ResponseEntity.ok(createUseCase.execute(dto))
    }
}
