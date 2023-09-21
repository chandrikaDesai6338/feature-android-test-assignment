package com.example.shacklehotelbuddy.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shacklehotelbuddy.compose.SearchUiState
import com.example.shacklehotelbuddy.repository.SearchApi
import com.example.shacklehotelbuddy.models.Child
import com.example.shacklehotelbuddy.models.Date
import com.example.shacklehotelbuddy.models.HotelSearchRequest
import com.example.shacklehotelbuddy.models.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(
    val searchApi: SearchApi
): ViewModel() {

    private val internalState = InternalState()
    internal val state: MutableStateFlow<SearchUiState> = MutableStateFlow(
        SearchUiState(
            checkInDate = internalState.checkInDate,
            checkOutDate = internalState.checkOutDate,
            person = internalState.person,
            children = internalState.children
        )
    )

    internal val recentSearches: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState(
        checkInDate = internalState.checkInDate,
        checkOutDate = internalState.checkOutDate,
        person = internalState.person,
        children = internalState.children
    ))

    internal fun onCheckInDateClicked(year: Int, month: Int, dayOfMonth: Int) {
        state.update {
            it.copy(
                checkInDate = "${dayOfMonth} / ${month} / ${year}",
            )
        }
    }
    internal fun onCheckOutDateClicked(year: Int, month: Int, dayOfMonth: Int) {
        state.update {
            it.copy(
                checkOutDate = "${dayOfMonth} / ${month} / ${year}",
            )
        }
    }

    internal fun onPersonClicked(count: Int) {
        state.update {
            it.copy(
                person = count,
            )
        }
    }

    internal fun onChildrenClicked(count: Int) {
        state.update {
            it.copy(
                children = count,
            )
        }
    }

    fun onSearchClicked() {
        recentSearches.update {
            it.copy(
                checkInDate = state.value.checkInDate,
                checkOutDate = state.value.checkOutDate,
                person = state.value.person,
                children = state.value.children
            )
        }
        CoroutineScope(Dispatchers.Default).launch {
            val response = searchApi.searchList(
                hotelSearchRequest =
                HotelSearchRequest(
                    checkOutDate = Date(1, 11, 2023),
                    checkInDate = Date(1, 10, 2023),
                    rooms = listOf(
                        Room(
                            adults = state.value.person,
                            children = listOf(
                                Child(
                                    age = 3
                                )
                            ),
                        )
                    )
                )
            )
            Log.d("Response Chan = ", response.toString())

        }
    }
}

private data class InternalState (
    val checkInDate: String = "DD/ MM/ YYYY",
    val checkOutDate: String = "DD/ MM/ YYYY",
    val person: Int = 1,
    val children: Int = 0
)
