package io.vitamax.singleschematenantdemo.domain.store

import io.vitamax.singleschematenantdemo.domain.store.dto.CreateStoreDto
import io.vitamax.singleschematenantdemo.domain.store.usecase.CreateStoreUseCase
import io.vitamax.singleschematenantdemo.domain.store.usecase.FindStoreUseCase
import io.vitamax.singleschematenantdemo.infra.TenantFilter
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*


@Validated
@RestController
@RequestMapping("/stores")
class StoreController(
    private val findUseCase: FindStoreUseCase,
    private val createUseCase: CreateStoreUseCase,
) {

    @TenantFilter
    @GetMapping("", "/")
    fun findAll(): ResponseEntity<Any> {
        return ResponseEntity.ok(object {
            val data = findUseCase.findAll()
        })
    }

    @TenantFilter
    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Store> {
        return ResponseEntity.ok(findUseCase.findById(id))
    }

    @PostMapping("")
    fun create(
        @RequestBody dto: CreateStoreDto,
    ): ResponseEntity<Store> {
        return ResponseEntity.ok(createUseCase.execute(dto).toDomain(emptyList()))
    }
}
