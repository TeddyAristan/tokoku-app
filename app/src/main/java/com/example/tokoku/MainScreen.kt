package com.example.tokoku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState
import com.example.tokoku.ui.theme.TokoKuTheme

@Composable
fun MainScreen(
    state:FormState,
    onEvent:(FormEvent) -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomBar(navController = navController)}
    ) {
        innerPadding->
        Box(modifier = Modifier.padding(
            PaddingValues(bottom = innerPadding.calculateBottomPadding()))
        ){
            BottomNavGraph(navController = navController,state=state, onEvent=onEvent)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Form,
        BottomBarScreen.Home,
        BottomBarScreen.CastReceipt
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.blue_sea),
        contentColor = Color.White
    ){
        screens.forEach{ screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
){
    BottomNavigationItem(
      label = {
          Text(text = screen.title)
      },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
            )
        },
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop= true
            }
        }
    )
}

@Composable
fun MainScreenPreview(state:FormState, onEvent: (FormEvent) -> Unit){
    TokoKuTheme{
        MainScreen(state = state, onEvent = onEvent)
    }
}