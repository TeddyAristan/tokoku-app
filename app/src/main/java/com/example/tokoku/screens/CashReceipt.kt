package com.example.tokoku.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tokoku.AllThings
import com.example.tokoku.R
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashReceiptScreen(
    navController:NavHostController,
    state:FormState,
    onEvent: (FormEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Manajemen Pelanggan Kasbon", overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)},
                modifier = modifier
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(5.dp))
                    .background(Color.White),
            )
        },
        floatingActionButton ={
            FloatingActionButton(onClick = { onEvent(FormEvent.ShowDialog) },modifier = modifier
                .padding(16.dp)
                .size(56.dp)
                .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        if(state.isAddingCustomer){
            AddCustomersDialog(state,onEvent)
        }
        Column(modifier= modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight()
        ){
            AllThings(navController=navController, state=state, onEvent = onEvent, type = "customer")
        }
    }
}