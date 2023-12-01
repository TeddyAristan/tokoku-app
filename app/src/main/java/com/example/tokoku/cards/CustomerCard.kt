package com.example.tokoku.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tokoku.entity.Customers
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState
import com.example.tokoku.screens.DetailCustomerKasbon

@Composable
fun CustomerCard (customer:Customers, state: FormState,onEvent:(FormEvent)->Unit){

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .size(width = 240.dp, height = 100.dp)
            .background(color = Color.White)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clickable {onEvent(FormEvent.ShowEditDialog) },
        shape = MaterialTheme.shapes.medium,
        colors=CardDefaults.cardColors(containerColor = Color.White)
    ){
        if(state.isEditingCustomer){
            DetailCustomerKasbon(id = customer.id, state = state, onEvent = onEvent)
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp)
            .background(color = Color.White)
        ){
            Box(modifier=Modifier.fillMaxWidth()){
                Text(text = customer.customerName, color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start, style = MaterialTheme.typography.bodyMedium)
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            ){
                Box(modifier = Modifier.wrapContentHeight()
                    ,contentAlignment = Alignment.CenterStart){
                    Text(text = customer.paymentDate, color = Color.DarkGray, fontWeight = FontWeight.Medium)
                }
                Box(modifier=Modifier.fillMaxWidth()
                    , contentAlignment = Alignment.CenterEnd){
                    Text(text ="Rp ${customer.nominalPayment}", color = Color.DarkGray, textAlign = TextAlign.Start, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                ,contentAlignment = Alignment.CenterStart){
                Text(text = "Status : ${customer.paymentStatus}", fontWeight = FontWeight.Medium,color = Color.LightGray)
            }
        }
    }
}