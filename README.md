# Multi-Tenancy in Single Schema Demo

## 1. Project Overview
This is a demo project to guide you how to implement multi tenant under a single schema environment. 


## 2. Getting Started
### Prerequisites
- JDK 17
- Spring Boot 3
- H2
- QueryDsl

### How to Run
1. shell
```shell
$ ./gradle bootJar
$ java -jar build/libs/single-schema-tenant-demo-0.0.1-SNAPSHOT.jar 
```
2. docker
```shell
$ ./build_and_run.sh
```

## 3. Core idea
The core implementation of this project leverages [Hibernate's `@Filter`](https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/Hibernate_User_Guide.html#pc-filter) feature. </br>
With this, you can selectively apply predefined conditions wherever you want with Spring AOP and custom annotations.

### 3.1 Declaration

#### 3.1.1) [StoreEntity](src/main/kotlin/io/vitamax/singleschematenantdemo/domain/store/StoreEntity.kt)
```kotlin
@Entity
@Table(
    name = "store",
    uniqueConstraints = [
        UniqueConstraint(
            name = "store_name_unique_const",
            columnNames = ["name"]
        )
    ]
)
@FilterDef(
    name = TENANT_FILTER_NAME,
    parameters = [ParamDef(name = "tenant_id", type = UUID::class)],
    defaultCondition = "tenant_id is not null"
)
@Filter(name = TENANT_FILTER_NAME, condition = TENANT_FILTER_CONDITION)
class StoreEntity (
    @Column
    val name: String = "",
) {
    @Id
    val id: UUID = UUID.randomUUID()

    @Column
    lateinit var tenantId: UUID

    fun toDomain(employees: List<Employee>) = Store(
        id = id,
        name = name,
        employees = employees,
        tenantId = tenantId,
    )
}
```

#### 3.1.2) [TenantFilter](src/main/kotlin/io/vitamax/singleschematenantdemo/infra/TenantFilter.kt)
```kotlin
package io.vitamax.singleschematenantdemo.infra

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@Target(CLASS, AnnotationTarget.FUNCTION)
@Retention(RUNTIME)
annotation class TenantFilter
```

#### 3.1.3) [TenantFilterAspect](src/main/kotlin/io/vitamax/singleschematenantdemo/infra/TenantFilterAspect.kt)

```kotlin
package io.vitamax.singleschematenantdemo.infra

// ... snip

@Aspect
@Component
class TenantFilterAspect(
    private val findTenantUseCase: FindTenantUseCase,
    private val em: EntityManager,
    private val request: HttpServletRequest,
) {

    @Pointcut("@within(io.vitamax.singleschematenantdemo.infra.TenantFilter)")
    fun onClass() {}

    @Pointcut("@annotation(io.vitamax.singleschematenantdemo.infra.TenantFilter)")
    fun onFunction() {}

    @Around("onClass() || onFunction()")
    fun applyFilter(joinPoint: ProceedingJoinPoint): Any? {
        val session = em.unwrap(Session::class.java)
        enableFilter(session)

        val result = joinPoint.proceed()

        disableFilter(session)

        return result
    }

    private fun enableFilter(session: Session) {
        if (!session.isOpen) {
            return
        }
        val tenantIdStr = request.getHeader(TENANT_FILTER_HEADER) ?: return
        val tenantId = UUID.fromString(tenantIdStr)
        val tenant = findTenantUseCase.findById(tenantId)
        if (tenant.isPrivileged) {
            return
        }
        session.enableFilter(TENANT_FILTER_NAME)
            .setParameter("tenant_id", tenantId)
    }

    private fun disableFilter(session: Session) {
        session.disableFilter(TENANT_FILTER_NAME)
    }

}
```

### 3.2 Usage
#### [StoreController](src/main/kotlin/io/vitamax/singleschematenantdemo/domain/store/StoreController.kt)

You can apply the custom annotation you define on a class, a method or whichever target you'd like to. 

```kotlin
package io.vitamax.singleschematenantdemo.domain.store
// ... snip

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
```


## Note
- `@Filter` in Hibernate < 6
  - does not apply to `direct fetching`
  - refer to [the official documentation](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#pc-filter)
- `@Filter` in Hibernate >= 6
  - applies to `direct fetching`
  - refer to [the official documentation](https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/Hibernate_User_Guide.html#pc-filter)

Also Hibernate session is closed when the main thread finishes its task and the filter won't apply. <br>
You should find a workaround like creating a new session by SessionFactory.
