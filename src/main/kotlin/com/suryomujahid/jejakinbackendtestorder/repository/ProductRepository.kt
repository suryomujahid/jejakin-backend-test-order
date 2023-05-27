package com.suryomujahid.jejakinbackendtestorder.repository

import com.suryomujahid.jejakinbackendtestorder.model.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "products")
interface ProductRepository : MongoRepository<Product, String> {
//    fun findByName(@Param("name") name: String): List<Product>
//
//    fun findByNameContains(@Param("name") name: String): List<Product>
}
