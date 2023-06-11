package io.vitamax.singleschematenantdemo.domain.store

import io.vitamax.singleschematenantdemo.domain.store.QStoreEntity.storeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.util.*


interface StoreRepository:
    JpaRepository<StoreEntity, UUID>,
    StoreCustomRepository

interface StoreCustomRepository {
    fun findList(): List<StoreEntity>
}

@Repository
class StoreRepositoryImpl:
    QuerydslRepositorySupport(StoreEntity::class.java),
    StoreCustomRepository {

    override fun findList(): List<StoreEntity> {
        return from(storeEntity)
            .fetch()
    }
}
