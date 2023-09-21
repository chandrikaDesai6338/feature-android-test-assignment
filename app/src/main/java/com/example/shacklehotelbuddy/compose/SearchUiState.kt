package com.example.shacklehotelbuddy.compose

data class SearchUiState(
    val checkInDate: String,
    val checkOutDate: String,
    val person: Int,
    val children: Int
)
