package io.vitamax.singleschematenantdemo.common

open class NotFoundException(
    entity: String,
    id: Any,
): Exception("$entity not found by id $id")
