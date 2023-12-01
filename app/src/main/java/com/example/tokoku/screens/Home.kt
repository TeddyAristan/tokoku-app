package com.example.tokoku.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tokoku.AllThings
import com.example.tokoku.MainActivity
import com.example.tokoku.MainScreen
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState
import com.example.tokoku.model.FormViewModel

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    navController : NavHostController,
    state:FormState,
    onEvent: (FormEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val modifier = Modifier
    LaunchedEffect(emptyList<String>()) {
        onEvent(FormEvent.Clear)
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Manajemen Harga Barang", overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)},
                modifier = modifier
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(5.dp))
                    .background(Color.White),
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        Column(modifier= modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Row(
                modifier= modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ){

                OutlinedTextField(
                    value= state.searchText,
                    modifier = Modifier
                        .background(
                            color = Color(234, 234, 234),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .weight(0.7f)
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    onValueChange = {
                        onEvent(FormEvent.SearchWordChanged(it))
                    },
                    placeholder = {Text(text="Cari nama barang")}
                )
                Button( modifier = modifier
                    .weight(0.3f)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 8.dp),onClick = { onEvent(FormEvent.SearchWord) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                    Text("Cari")
                }
            }
            AllThings(navController=navController, state=state, onEvent = onEvent, type = "forms")

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPreview(navController: NavHostController,state: FormState, onEvent: (FormEvent) -> Unit){
    HomeScreen(navController,state,onEvent)
}