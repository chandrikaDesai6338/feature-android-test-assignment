package com.example.shacklehotelbuddy.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shacklehotelbuddy.R
import com.example.shacklehotelbuddy.models.Hotel
import com.example.shacklehotelbuddy.ui.theme.ShackleHotelBuddyTheme
import com.example.shacklehotelbuddy.viewModel.ListingViewModel

@Composable
fun ListingScreen(navController: NavController) {
    val viewModel = hiltViewModel<ListingViewModel>()
    val state by viewModel.state.collectAsState()

    ListingComponent(state,
        backNavigation = {
            navController.navigate("MainScreen")
        })
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingComponent(
    hotels: List<Hotel>,
    backNavigation: () -> Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hotel List") },
                navigationIcon = {
                    IconButton(
                        onClick = backNavigation
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            items(hotels) { hotel ->
                HotelListItem(hotel = hotel)
            }
        }
    }
}

@Composable
fun HotelListItem(hotel: Hotel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = ShackleHotelBuddyTheme.colors.grayBorder,
                    shape = RoundedCornerShape(corner = CornerSize(24.dp))
                )
                .padding(16.dp)
        ) {
            // Hotel Image
            Image(
                painter = painterResource(id = R.drawable.hotel),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Hotel Name
            Text(
                text = hotel.name,
                style = ShackleHotelBuddyTheme.typography.title2,
                fontWeight = FontWeight.Bold
            )

            // City and Country
            Text(
                text = "${hotel.city}, ${hotel.country}",
                style = ShackleHotelBuddyTheme.typography.subtitle2,
                color = ShackleHotelBuddyTheme.colors.grayText
            )

            // Price
            Text(
                text = hotel.price,
                style = ShackleHotelBuddyTheme.typography.bodyMedium,
                color = ShackleHotelBuddyTheme.colors.grayText
            )
        }
    }
}