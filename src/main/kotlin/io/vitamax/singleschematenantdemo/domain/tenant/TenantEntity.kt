package io.vitamax.singleschematenantdemo.domain.tenant

import jakarta.persistence.*
import java.util.*


@Entity
@Table(
    name = "tenant",
    uniqueConstraints = [
        UniqueConstraint(
            name = "tenant_name_unique_const",
            columnNames = ["name"]
        )
    ]
)
class TenantEntity (
    @Column
    val name: String = "",

    @Column
    var isPrivileged: Boolean = false,
) {
    @Id
    val id: UUID = UUID.randomUUID()

    fun toDomain() = Tenant(
        id = id,
        name = name,
        isPrivileged = isPrivileged,
    )
}
