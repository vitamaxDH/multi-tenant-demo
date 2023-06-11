package io.vitamax.singleschematenantdemo.domain.employee

import io.vitamax.singleschematenantdemo.common.NotFoundException
import java.util.*

class EmployeeNotFoundException(
    val id: UUID,
): NotFoundException("employee", id)
