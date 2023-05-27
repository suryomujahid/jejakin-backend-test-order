package com.suryomujahid.jejakinbackendtestorder.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "orders")
data class Order (
    @Id var id: String?,

    @NotBlank(message = "User ID is required")
    @NotNull(message = "User ID is required")
    var userId: String,

    @NotBlank(message = "Product ID is required")
    @NotNull(message = "Product is required")
    var productId: String,

    @NotNull(message = "Amount is required")
    var amount: Int,

    @NotBlank(message = "Status is required")
    @NotNull(message = "Status is required")
    var status: String
) {
    var user: User? = null
    var product: Product? = null
}
