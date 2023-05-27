package com.suryomujahid.jejakinbackendtestorder.controller

import com.suryomujahid.jejakinbackendtestorder.model.BaseResponse
import com.suryomujahid.jejakinbackendtestorder.model.Product
import com.suryomujahid.jejakinbackendtestorder.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productRepository: ProductRepository

    @GetMapping
    fun getAll(): ResponseEntity<BaseResponse> {
        return try {
            val products = productRepository.findAll()

            ResponseEntity.ok(BaseResponse(
                "success",
                "Products found",
                products
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse(
                "error",
                e.message ?: "Products not found",
                null
            ))
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<BaseResponse> {
        return try {
            val product = productRepository.findById(id).orElseThrow { Exception("Product not found") }

            ResponseEntity.ok(BaseResponse(
                "success",
                "Product found",
                product
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse(
                "error",
                e.message ?: "Product not found",
                null
            ))
        }
    }

    @PostMapping
    fun create(@RequestBody request: Product): ResponseEntity<BaseResponse> {
        return try {
            val product = productRepository.save(request)

            ResponseEntity.ok(BaseResponse(
                "success",
                "Product created",
                product
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse(
                "error",
                e.message ?: "Product not created",
                null
            ))
        }
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody request: Product): ResponseEntity<BaseResponse> {
        return try {
            val product = productRepository.findById(id).orElseThrow { Exception("Product not found") }

            product.name = request.name

            productRepository.save(product)

            ResponseEntity.ok(BaseResponse(
                "success",
                "Product updated",
                product
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse(
                "error",
                e.message ?: "Product not updated",
                null
            ))
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<BaseResponse> {
        return try {
            val product = productRepository.findById(id).orElseThrow { Exception("Product not found") }

            productRepository.delete(product)

            ResponseEntity.ok(BaseResponse(
                "success",
                "Product deleted",
                null
            ))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse(
                "error",
                e.message ?: "Product not deleted",
                null
            ))
        }
    }
}
