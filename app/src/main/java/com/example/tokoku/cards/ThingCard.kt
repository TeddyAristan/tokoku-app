package com.example.tokoku.cards

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.tokoku.entity.Form

@Composable
fun ThingCard(form: Form,navController: NavHostController) {
    val id = form.id.toString()
    val uri = Uri.parse(form.imageURI)

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .size(width = 240.dp, height = 100.dp)
            .background(Color.White)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
            .clickable { navController.navigate("detailFormDataPage/$id") },
        shape = MaterialTheme.shapes.medium,
        )
    {

        Row(
           verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(color=Color.White)
        ){
            Image(
                painter = rememberImagePainter(data = uri),
                contentDescription = "Image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(8.dp)
                , contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
            ){
                Box(modifier=Modifier.fillMaxWidth()){
                    Text(text = form.name, color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start, style = MaterialTheme.typography.bodyMedium)
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                ){
                    Box(modifier = Modifier.wrapContentHeight()
                    ,contentAlignment = Alignment.CenterStart){
                        Text(text = form.type, color = Color.LightGray, fontWeight = FontWeight.Medium)
                    }
                    Box(modifier=Modifier.fillMaxWidth()
                    , contentAlignment = Alignment.CenterEnd){
                        Text(text = "Rp ${form.minPrice} - Rp ${form.maxPrice}", color = Color.LightGray, textAlign = TextAlign.Start, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    ,contentAlignment = Alignment.CenterStart){
                    Text(text = "Kuantitas : ${form.quantity}", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
