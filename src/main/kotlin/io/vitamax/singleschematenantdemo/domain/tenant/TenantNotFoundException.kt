package io.vitamax.singleschematenantdemo.domain.tenant

import io.vitamax.singleschematenantdemo.common.NotFoundException
import java.util.*

class TenantNotFoundException(
    val id: UUID,
): NotFoundException("tenant", id)
