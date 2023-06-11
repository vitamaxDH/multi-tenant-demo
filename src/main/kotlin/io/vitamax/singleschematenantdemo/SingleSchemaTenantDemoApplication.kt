package io.vitamax.singleschematenantdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SingleSchemaTenantDemoApplication

fun main(args: Array<String>) {
    runApplication<SingleSchemaTenantDemoApplication>(*args)
}
