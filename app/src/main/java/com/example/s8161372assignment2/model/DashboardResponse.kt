package com.example.s8161372assignment2.model

data class DashboardResponse(
    val entities: List<Map<String, Any?>>,
    val entityTotal: Int
)