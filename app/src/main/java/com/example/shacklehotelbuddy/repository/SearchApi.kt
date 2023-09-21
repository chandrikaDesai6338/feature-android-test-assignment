package com.example.shacklehotelbuddy.repository

import com.example.shacklehotelbuddy.models.HotelSearchRequest
import com.example.shacklehotelbuddy.models.HotelSearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SearchApi {

    @POST("properties/v2/list")
    @Headers("X-RapidAPI-Key: b2077fed9bmsh98bbb728fcf9693p15f5acjsn202aaddff215")
    suspend fun searchList(@Body hotelSearchRequest: HotelSearchRequest): Response<HotelSearchResponse>
}