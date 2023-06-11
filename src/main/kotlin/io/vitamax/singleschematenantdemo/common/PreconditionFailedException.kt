package io.vitamax.singleschematenantdemo.common

class PreconditionFailedException(
    override val message: String,
): Exception(message)
