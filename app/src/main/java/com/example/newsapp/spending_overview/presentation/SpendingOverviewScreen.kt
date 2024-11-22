package com.example.newsapp.spending_overview.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.core.domain.Spending
import com.example.newsapp.core.presentation.theme.montserrat
import com.example.newsapp.core.presentation.util.Background
import com.example.newsapp.spending_overview.presentation.utils.formatDate
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingOverviewScreenCore(
    viewModel: SpendingOverviewViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit
) {
    LaunchedEffect(true) {
        viewModel.onAction(SpendingOverviewActions.LoadSpendingOverviewAndBalance)
    }
    SpendingOverviewCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBalanceClick = onBalanceClick,
        onAddSpendingClick = onAddSpendingClick,
        onDeleteSpending = {
            viewModel.onAction(SpendingOverviewActions.OnDeleteSpending(it))
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingOverviewCoreScreen(
    state: SpendingOverviewState,
    onAction: (SpendingOverviewActions) -> Unit,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onDeleteSpending: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar =
        {
            Column {
                SpendingOverviewTopAppBar(
                    balance = state.balance.toString(),
                    scrollBehavior = scrollBehavior,
                    onBalanceClicked = onBalanceClick
                )
                Spacer(modifier = Modifier.height(10.dp))

                DatePickerDropDownMenu(state = state, onDatePicked = { index ->
                    onAction(SpendingOverviewActions.OnDateChanged(index))
                })
            }

        },
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = onAddSpendingClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Spending")
                }
                Spacer(Modifier.height(40.dp))
            }
        }

    )
    { contentPadding ->

        Background()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            SpendingList(state = state, onDeleteSpending = { onDeleteSpending(it) })
        }

    }

}

@Composable
fun SpendingList(
    modifier: Modifier = Modifier,
    state: SpendingOverviewState,
    onDeleteSpending: (Int) -> Unit
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        itemsIndexed(state.spendingList) { index, spending ->
            SpendingItem(spending = spending, onDeleteClick = { onDeleteSpending(index) })
            Spacer(Modifier.height(16.dp))
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpendingItem(modifier: Modifier = Modifier, spending: Spending, onDeleteClick: () -> Unit) {
    var isDeleteShowing by rememberSaveable { mutableStateOf(false) }
    Box {
        ElevatedCard(
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            modifier = modifier
                .height(150.dp)
                .padding(horizontal = 16.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = { isDeleteShowing = !isDeleteShowing }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color(spending.color))
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = spending.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    fontSize = 23.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(1.dp))
                SpendingInfo(name = "Price", value = "$${spending.price}")
                Spacer(Modifier.height(1.dp))
                SpendingInfo(name = "Kilograms", value = "${spending.kilograms}")
                Spacer(Modifier.height(1.dp))
                SpendingInfo(name = "Quantity", value = "${spending.quantity}")
            }
        }
        DropdownMenu(
            expanded = isDeleteShowing,
            onDismissRequest = { isDeleteShowing = false },
            offset = DpOffset(30.dp, 0.dp)
        ) {
            DropdownMenuItem(
                text = { Text(text = "Delete Spending", fontFamily = montserrat) },
                onClick = {
                    isDeleteShowing = false
                    onDeleteClick()
                })
        }
    }
}

@Composable
fun SpendingInfo(name: String, value: String, modifier: Modifier = Modifier) {

    Row {
        Text(
            text = "$name :",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
        )
        Text(
            text = "$value :",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DatePickerDropDownMenu(state: SpendingOverviewState, onDatePicked: (Int) -> Unit) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(start = 12.dp)
            .shadow(elevation = 0.5.dp, shape = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            offset = DpOffset(10.dp, 0.dp),
            modifier = Modifier.heightIn(max = 440.dp)
        ) {
            state.dateList.forEachIndexed { index, zonedDateTime ->
                if (index == 0) {

                    Spacer(Modifier.height(4.dp))
                }

                Text(
                    text = zonedDateTime.formatDate(),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            isExpanded = false
                            onDatePicked(index)
                        }
                )
                Spacer(Modifier.height(4.dp))
            }

        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable {
                    isExpanded = true
                }
                .padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = state.selectedDate.formatDate(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Pick a date")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingOverviewTopAppBar(
    modifier: Modifier = Modifier,
    balance: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onBalanceClicked: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ), scrollBehavior = scrollBehavior, title = {
            Text(text = "$$balance", fontSize = 30.sp, maxLines = 1, fontFamily = montserrat)
        },
        actions = {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(0.6f),
                        shape = RoundedCornerShape(13.dp)
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.3f))
                    .clickable {
                        onBalanceClicked()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Preview
@Composable
private fun SpendingOverviewCoreScreenPreview(modifier: Modifier = Modifier) {
    SpendingOverviewCoreScreen(
        state = SpendingOverviewState(),
        onAction = {},
        onAddSpendingClick = {},
        onDeleteSpending = {},
        onBalanceClick = {})
}