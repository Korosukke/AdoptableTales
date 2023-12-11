package com.example.ca3

data class NotificationModel(
    val id: Int,
    val image: Int?,
    val name: String?, // Declare name as a property directly
    val type: String?,
    val desc: String?
)
