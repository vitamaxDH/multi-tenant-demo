package io.vitamax.singleschematenantdemo.common

object HibernateFilterConstant {
    const val TENANT_FILTER_HEADER = "x-tenant-id"
    const val TENANT_FILTER_NAME = "tenantFilter"
    const val TENANT_FILTER_CONDITION = "tenant_id = :tenant_id"
}
