package com.cdesai.shacklesearch.compose

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shacklehotelbuddy.R
import com.example.shacklehotelbuddy.compose.SearchUiState
import com.example.shacklehotelbuddy.ui.theme.ShackleHotelBuddyTheme
import com.example.shacklehotelbuddy.viewModel.SearchViewModel
import java.util.Calendar

@Composable
fun MainScreen(navController: NavController) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val state by viewModel.state.collectAsState()
    val recentSearch by viewModel.recentSearches.collectAsState()
    SearchComponent(
        state = state,
        recentSearch = recentSearch,
        onCheckInDateClicked = { selectedYear, selectedMonth, selectedDayOfMonth ->
            viewModel.onCheckInDateClicked(selectedYear, selectedMonth, selectedDayOfMonth)
        },
        onCheckOutDateClicked = { selectedYear, selectedMonth, selectedDayOfMonth ->
            viewModel.onCheckOutDateClicked(selectedYear, selectedMonth, selectedDayOfMonth)},
        onPersonClicked = { count ->
            viewModel.onPersonClicked(count)
        },
        onChildrenClicked = { count ->
            viewModel.onChildrenClicked(count)
        },
        onSearchClicked = {
            viewModel.onSearchClicked()
            navController.navigate("ListingScreen")
        }
    )
}

@Composable
fun SearchComponent(
    state: SearchUiState,
    recentSearch: SearchUiState,
    onCheckInDateClicked: (selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) -> Unit,
    onCheckOutDateClicked: (selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) -> Unit,
    onPersonClicked: (Int) -> Unit,
    onChildrenClicked: (Int) -> Unit,
    onSearchClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillWidth
            ),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .padding(all = 16.dp),
                text = "Select guests, date and time",
                style = ShackleHotelBuddyTheme.typography.title,
                color = Color.White
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(corner = CornerSize(28.dp))
                    )
            ) {
                CheckInDate(
                    iconResId = R.drawable.event_upcoming,
                    startContentText = "Check-In date",
                    datesText = state.checkInDate,
                    onCheckInDateClicked = onCheckInDateClicked
                )
                checkOutDate(
                    iconResId = R.drawable.event_upcoming,
                    startContentText = "Check-Out date",
                    datesText = state.checkOutDate,
                    onCheckOutDateClicked = onCheckOutDateClicked
                )
                PersonSelector(
                    iconResId = R.drawable.person,
                    startContentText = "Person",
                    personCount = state.person,
                    onPersonClicked = onPersonClicked
                )
                ChildrenSelector(
                    iconResId = R.drawable.supervisor_account,
                    startContentText = "Children",
                    childrenCount = state.children,
                    onChildrenClicked = onChildrenClicked
                )
            }

            Text(
                modifier = Modifier
                    .padding(all = 16.dp),
                text = "Recent Search",
                style = ShackleHotelBuddyTheme.typography.title,
                color = Color.White
            )

            RecentSearch(recentSearch = recentSearch)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = onSearchClicked) {
                Text(
                    modifier = Modifier.padding(all = 8.dp),
                    style = ShackleHotelBuddyTheme.typography.subtitle,
                    text = "Search")
            }
        }
    }
}

@Composable
fun RecentSearch(
    recentSearch: SearchUiState
) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(all = 16.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(24.dp))
        )) {
        Row(modifier = Modifier.padding(all = 16.dp)) {
            Icon(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.manage_history),
                contentDescription = null
            )
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .background(color = ShackleHotelBuddyTheme.colors.grayBorder)
            )
            Text(
                modifier = Modifier.padding(all = 8.dp),
                style = ShackleHotelBuddyTheme.typography.bodyMedium,
                text = "${recentSearch.checkInDate} - ${recentSearch.checkOutDate}"
            )
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(20.dp)
                    .background(color = ShackleHotelBuddyTheme.colors.grayBorder)
            )
            Text(
                modifier = Modifier.padding(all = 8.dp),
                style = ShackleHotelBuddyTheme.typography.bodyMedium,
                text = "${recentSearch.person} adult, ${recentSearch.children} children"
            )
        }
    }
}
@Composable
fun CheckInDate(
    iconResId: Int,
    startContentText: String,
    datesText: String,
    onCheckInDateClicked: (selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) -> Unit
) {
    var showCalendar by remember { mutableStateOf(false) }
    SearchRow(
        iconResId = iconResId,
        startContentText = startContentText,
        endContentText = datesText,
        onClick = {
            showCalendar = true
        }
    )
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = ShackleHotelBuddyTheme.colors.grayBorder)
    )
    ShowCalendar(showCalendar, onClickCalendar = onCheckInDateClicked)
    showCalendar = false
}

@Composable
fun checkOutDate(
    iconResId: Int,
    startContentText: String,
    datesText: String,
    onCheckOutDateClicked: (selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) -> Unit
) {
    var showCalendar by remember { mutableStateOf(false) }
    SearchRow(
        iconResId = iconResId,
        startContentText = startContentText,
        endContentText = datesText,
        onClick = {
            showCalendar = true
        }
    )
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = ShackleHotelBuddyTheme.colors.grayBorder)
    )
    ShowCalendar(showCalendar, onClickCalendar = onCheckOutDateClicked)
    showCalendar = false
}

@Composable
fun PersonSelector(
    iconResId: Int,
    startContentText: String,
    personCount: Int,
    onPersonClicked: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    SearchRow(
        iconResId = iconResId,
        startContentText = startContentText,
        endContentText = personCount.toString(),
        onClick = {
            expanded = true
        }
    )
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = ShackleHotelBuddyTheme.colors.grayBorder)
    )

    DropDown(
        expanded = expanded,
        onClick = {
            onPersonClicked(it)
            expanded = false
        }, onDismiss = {
            expanded = false
        })
}

@Composable
fun ChildrenSelector(
    iconResId: Int,
    startContentText: String,
    childrenCount: Int,
    onChildrenClicked: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    SearchRow(
        iconResId = iconResId,
        startContentText = startContentText,
        endContentText = childrenCount.toString(),
        onClick = {
            expanded = true
        }
    )
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = ShackleHotelBuddyTheme.colors.grayBorder)
    )
    DropDown(
        expanded = expanded,
        onClick = {
            onChildrenClicked(it)
            expanded = false
        }, onDismiss = {
            expanded = false
        })
}

@Composable
private fun DropDown(
    expanded: Boolean,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val options =
        listOf(1, 2, 3, 4, 5)
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        options.forEachIndexed { index, option ->
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.padding(all = 2.dp),
                        text = option.toString(),
                        style = ShackleHotelBuddyTheme.typography.bodyMedium
                    )
                },
                onClick = {
                    onClick.invoke(option)
                })
        }
    }
}

@Composable
private fun ShowCalendar(
    showCalendar: Boolean,
    onClickCalendar: (selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int) -> Unit
) {
    val mContext = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val datePicker = DatePickerDialog(
        mContext,
        R.style.ThemeOverlay_MyApp_Dialog,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            onClickCalendar.invoke(selectedYear, selectedMonth, selectedDayOfMonth)
        }, year, month, dayOfMonth
    )
    if (showCalendar) {
        datePicker.show()
    }
}

@Composable
fun SearchRow(
    iconResId: Int,
    startContentText: String,
    endContentText: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = startContentText,
                    modifier = Modifier.padding(all = 2.dp)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 8.dp), text = startContentText,
                    style = ShackleHotelBuddyTheme.typography.bodyMedium
                )
            }
        }
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(20.dp)
                .background(color = ShackleHotelBuddyTheme.colors.grayBorder)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    onClick = onClick
                )
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 8.dp), text = endContentText,
                style = ShackleHotelBuddyTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    val colorFilter = if (tint == Color.Unspecified) null else ColorFilter.tint(tint)
    val semantics = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier
    }
    Box(
        modifier
            .toolingGraphicsLayer()
            .paint(
                painter,
                colorFilter = colorFilter,
                contentScale = ContentScale.Fit
            )
            .then(semantics)
    )
}