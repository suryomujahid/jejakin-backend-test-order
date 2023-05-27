package com.suryomujahid.jejakinbackendtestorder.repository

import com.suryomujahid.jejakinbackendtestorder.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "users")
interface UserRepository : MongoRepository<User, String> {

    fun findByEmail(@Param("email") email: String): List<User>
//    fun findByIdAndEmail(@Param("id") id: String, @Param("email") email: String): List<User>

    fun findByUsername(@Param("username") username: String): List<User>
//    fun findByIdAndUsername(@Param("id") id: String, @Param("username") username: String): List<User>
}
