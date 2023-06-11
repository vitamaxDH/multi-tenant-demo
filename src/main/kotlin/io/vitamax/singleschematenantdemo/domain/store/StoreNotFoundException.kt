package io.vitamax.singleschematenantdemo.domain.store

import io.vitamax.singleschematenantdemo.common.NotFoundException
import java.util.*

class StoreNotFoundException(
    val id: UUID,
): NotFoundException("store", id)
