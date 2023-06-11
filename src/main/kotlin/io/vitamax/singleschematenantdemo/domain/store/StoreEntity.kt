package io.vitamax.singleschematenantdemo.domain.store

import io.vitamax.singleschematenantdemo.common.HibernateFilterConstant.TENANT_FILTER_CONDITION
import io.vitamax.singleschematenantdemo.common.HibernateFilterConstant.TENANT_FILTER_NAME
import io.vitamax.singleschematenantdemo.domain.employee.Employee
import jakarta.persistence.*
import org.hibernate.annotations.Filter
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.ParamDef
import java.util.*

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
