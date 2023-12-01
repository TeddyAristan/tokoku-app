package com.example.tokoku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.tokoku.cards.CustomerCard
import com.example.tokoku.cards.ThingCard
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState

@Composable
fun AllThings(navController: NavHostController, state:FormState, onEvent:(FormEvent)->Unit,type:String) {
    val modifier = Modifier

    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(color = Color.White),
    ) {
        if(type == "forms"){

            items(state.forms){form->
                ThingCard(form,navController)
            }
        }else{
            items(state.customers){customer->
                CustomerCard(customer = customer, state,onEvent)
            }
        }
    }
}