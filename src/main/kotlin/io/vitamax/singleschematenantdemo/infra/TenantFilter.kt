package io.vitamax.singleschematenantdemo.infra

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS, AnnotationTarget.FUNCTION)
@Retention(RUNTIME)
annotation class TenantFilter
