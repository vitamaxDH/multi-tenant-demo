package io.vitamax.singleschematenantdemo.domain.tenant

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface TenantRepository: JpaRepository<TenantEntity, UUID>
