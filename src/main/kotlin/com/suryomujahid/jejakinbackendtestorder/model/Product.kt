package com.suryomujahid.jejakinbackendtestorder.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
data class Product (
    @Id var id: String?,
    var name: String
)
