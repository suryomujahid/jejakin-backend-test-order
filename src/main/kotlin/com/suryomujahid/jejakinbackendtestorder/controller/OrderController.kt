package com.suryomujahid.jejakinbackendtestorder.controller

import com.suryomujahid.jejakinbackendtestorder.model.BaseResponse
import com.suryomujahid.jejakinbackendtestorder.model.Order
import com.suryomujahid.jejakinbackendtestorder.repository.OrderRepository
import com.suryomujahid.jejakinbackendtestorder.repository.ProductRepository
import com.suryomujahid.jejakinbackendtestorder.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    @GetMapping
    fun getAll(@Param("userId") userId: String?): ResponseEntity<BaseResponse> {
        return try {
            val orders = if (!userId.isNullOrEmpty())
                orderRepository.findByUserId(userId) else
                orderRepository.findAll()

            if (orders.isNotEmpty()) {
                for (order in orders) {
                    order.user = userRepository.findById(order.userId).get()
                    order.product = productRepository.findById(order.productId).get()
                }
            }

            ResponseEntity.ok(BaseResponse(
                "success",
                "Orders found",
                orders
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse(
                "error",
                e.message ?: "Orders not found",
                null
            ))
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<BaseResponse> {
        return try {
            val order = orderRepository.findById(id).orElseThrow { Exception("Order not found") }

            order.user = userRepository.findById(order.userId).get()
            order.product = productRepository.findById(order.productId).get()

            ResponseEntity.ok(BaseResponse(
                "success",
                "Order found",
                order
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse(
                "error",
                e.message ?: "Order not found",
                null
            ))
        }
    }

    @PostMapping
    fun create(@RequestBody @Valid request: Order): ResponseEntity<BaseResponse> {
        return try {
            if (!userRepository.existsById(request.userId)) {
                throw Exception("User not found")
            }

            if (!productRepository.existsById(request.productId)) {
                throw Exception("Product not found")
            }

            val order = orderRepository.save(request)

            order.user = userRepository.findById(order.userId).get()
            order.product = productRepository.findById(order.productId).get()

            ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(
                "success",
                "Order created",
                order
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse(
                "error",
                e.message ?: "Order not created",
                null
            ))
        }
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody request: Order): ResponseEntity<BaseResponse> {
        return try {
            var order = orderRepository.findById(id).orElseThrow { Exception("Order not found") }

            order.status = request.status
            order.amount = request.amount

            order = orderRepository.save(order)

            order.user = userRepository.findById(order.userId).get()
            order.product = productRepository.findById(order.productId).get()

            ResponseEntity.ok(BaseResponse(
                "success",
                "Order updated",
                order
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse(
                    "error",
                    e.message ?: "Order not updated",
                    null
                )
            )
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<BaseResponse> {
        return try {
            val order = orderRepository.findById(id).orElseThrow { Exception("Order not found") }

            orderRepository.delete(order)

            ResponseEntity.ok(BaseResponse(
                "success",
                "Order deleted",
                null
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse(
                    "error",
                    e.message ?: "Order not deleted",
                    null
                )
            )
        }
    }
}
