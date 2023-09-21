package com.example.shacklehotelbuddy.viewModel

import androidx.lifecycle.ViewModel
import com.example.shacklehotelbuddy.compose.SearchUiState
import com.example.shacklehotelbuddy.models.Hotel
import com.example.shacklehotelbuddy.repository.SearchApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    val searchApi: SearchApi
) : ViewModel() {

    init {
        searchApi
    }
    internal val state: MutableStateFlow<List<Hotel>> = MutableStateFlow(
        listOf(
            Hotel(
                name = "Beverly Hotel",
                city = "Amsterdam",
                country = "Netherlands",
                price = "€ 100 night",
                imageUrl = ""
            ),
            Hotel(
                name = "Amsterdam's Finest Hotel",
                city = "Amsterdam",
                country = "Netherlands",
                price = "€ 500 night",
                imageUrl = ""
            )
        )
    )

}