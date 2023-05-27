package com.suryomujahid.jejakinbackendtestorder.controller

import com.suryomujahid.jejakinbackendtestorder.model.BaseResponse
import com.suryomujahid.jejakinbackendtestorder.model.User
import com.suryomujahid.jejakinbackendtestorder.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping
    fun getAll(): ResponseEntity<BaseResponse> {
        return try {
            val users = userRepository.findAll()

            ResponseEntity.ok(BaseResponse(
                "success",
                "Users found",
                users
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse(
                "error",
                e.message ?: "Users not found",
                null
            ))
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<BaseResponse> {
        return try {
            val user = userRepository.findById(id).orElseThrow { Exception("User not found") }

            ResponseEntity.ok(BaseResponse(
                "success",
                "User found",
                user
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse(
                "error",
                e.message ?: "User not found",
                null
            ))
        }
    }

    @PostMapping
    fun create(@RequestBody request: User): ResponseEntity<BaseResponse> {
        return try {
            if (userRepository.findByEmail(request.email).isNotEmpty())
                throw Exception("Email already exist")
            if (userRepository.findByUsername(request.username).isNotEmpty())
                throw Exception("Username already exist")
            val user = userRepository.save(request)

            ResponseEntity.ok(BaseResponse(
                "success",
                "User created",
                user
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse(
                "error",
                e.message ?: "User not created",
                null
            ))
        }
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody request: User): ResponseEntity<BaseResponse> {
        return try {
            if (userRepository.findByEmail(request.email).filterNot { it.id == id }.isNotEmpty())
                throw Exception("Email already exist")
            if (userRepository.findByUsername(request.username).filterNot { it.id == id }.isNotEmpty())
                throw Exception("Username already exist")

            val user = userRepository.findById(id).orElseThrow { Exception("User not found") }

            user.username = request.username
            user.firstName = request.firstName
            user.lastName = request.lastName
            user.email = request.email
            user.role = request.role

            userRepository.save(user)

            ResponseEntity.ok(BaseResponse(
                "success",
                "User updated",
                user
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse(
                "error",
                e.message ?: "User not updated",
                null
            ))
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<BaseResponse> {
        return try {
            val user = userRepository.findById(id).orElseThrow { Exception("User not found") }

            userRepository.delete(user)

            ResponseEntity.ok(BaseResponse(
                "success",
                "User deleted",
                null
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse(
                "error",
                e.message ?: "User not deleted",
                null
            ))
        }
    }
}
