package com.example.shacklehotelbuddy.models

data class HotelSearchRequest(
    val currency: String? = null,
    val eapid: Int? = null,
    val locale: String? = null,
    val siteId: Long? = null,
    val destination: Destination? = null,
    val checkInDate: Date? = null,
    val checkOutDate: Date? = null,
    val rooms: List<Room>? = null,
    val resultsStartingIndex: Int? = null,
    val resultsSize: Int? = null,
    val sort: String? = null,
    val filters: Filters? = null
)
