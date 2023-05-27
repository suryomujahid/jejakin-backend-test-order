package com.suryomujahid.jejakinbackendtestorder.repository

import com.suryomujahid.jejakinbackendtestorder.model.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "orders")
interface OrderRepository : MongoRepository<Order, String> {
    fun findByUserId(@Param("userId") userId: String): List<Order>

//    fun findByUserIdAndStatus(@Param("userId") userId: String, @Param("status") status: String): List<Order>
}

