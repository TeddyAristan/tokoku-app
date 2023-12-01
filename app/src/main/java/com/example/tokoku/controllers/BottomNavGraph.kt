package com.example.tokoku

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState
import com.example.tokoku.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraph(navController : NavHostController, state:FormState, onEvent:(FormEvent) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route= BottomBarScreen.Form.route){
            FormScreenPreview(state=state,onEvent=onEvent)
        }
        composable(route= BottomBarScreen.Home.route){
            HomeScreenPreview(navController,state=state, onEvent=onEvent)
        }
        composable(route= BottomBarScreen.CastReceipt.route){
            CashReceiptScreen(navController,state=state, onEvent=onEvent)
        }
        composable("detailFormDataPage/{id}"){backStackEntry->
            val parameterValue = backStackEntry.arguments?.getString("id")?:""
            DetailFormData(navController,parameterValue,state, onEvent)
        }
    }
}


