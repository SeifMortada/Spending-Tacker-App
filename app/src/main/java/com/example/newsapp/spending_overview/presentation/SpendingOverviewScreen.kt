package com.example.newsapp.spending_overview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.core.presentation.util.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingOverviewScreenCore(
    viewModel: SpendingOverviewViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit
) {
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
            TopAppBar(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ), title = { SpendingOverviewTopAppBar(balance = state.balance.toString()) }
            )
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

        }

    }

}

@Composable
fun SpendingOverviewTopAppBar(modifier: Modifier = Modifier, balance: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$$balance", fontSize = 30.sp)
        OutlinedIconButton(modifier = Modifier.wrapContentSize(), onClick = {}) {
            Icon(Icons.Default.AttachMoney, contentDescription = null)
        }
    }
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