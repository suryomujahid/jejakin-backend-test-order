package com.suryomujahid.jejakinbackendtestorder.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User (
    @Id var id: String?,
    var username: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var role: String
)
