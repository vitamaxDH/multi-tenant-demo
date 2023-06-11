package io.vitamax.singleschematenantdemo.infra

import io.vitamax.singleschematenantdemo.common.HibernateFilterConstant.TENANT_FILTER_HEADER
import io.vitamax.singleschematenantdemo.common.HibernateFilterConstant.TENANT_FILTER_NAME
import io.vitamax.singleschematenantdemo.domain.tenant.usecase.FindTenantUseCase
import jakarta.persistence.EntityManager
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.hibernate.Session
import org.springframework.stereotype.Component
import java.util.*

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
