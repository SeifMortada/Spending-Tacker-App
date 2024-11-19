package com.example.newsapp.balance.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.newsapp.core.presentation.theme.montserrat
import com.example.newsapp.core.presentation.util.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun BalanceScreenCore(viewModel: BalanceViewModel = koinViewModel(), onSaveClick: () -> Unit) {
    BalanceScreenCoreScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSaveClick =
        {
            viewModel.onAction(BalanceAction.OnBalanceSaved)
            onSaveClick()
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceScreenCoreScreen(
    state: BalanceState,
    onAction: (BalanceAction) -> Unit,
    onSaveClick: () -> Unit
) {

    Scaffold(topBar = {
        TopAppBar(modifier = Modifier.padding(start = 12.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent
            ),
            title = {
                Text(
                    text = "Update Balance",
                    fontFamily = montserrat,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            })

    }) { padding ->
        Background()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(62.dp))
            Text(
                text = "$${state.balance}",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.SemiBold,
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(38.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.balance.toString(),
                onValueChange = {
                    onAction(BalanceAction.OnBalanceChanged(it.toDoubleOrNull() ?: 0.0))
                },
                label = {
                    Text(text = "Enter Balance")
                },
                textStyle = TextStyle(fontSize = 18.sp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Spacer(modifier = Modifier.height(38.dp))
            OutlinedButton(
                onClick = { onSaveClick() }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(33.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Save Balance",
                        fontSize = 20.sp
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun BalanceScreenCoreScreenPreview(modifier: Modifier = Modifier) {
    BalanceScreenCoreScreen(state = BalanceState(), onAction = {}, onSaveClick = {})
}
