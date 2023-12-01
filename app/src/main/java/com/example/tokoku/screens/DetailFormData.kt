package com.example.tokoku.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
//import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.tokoku.R
import com.example.tokoku.entity.Form
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState

fun getItemById(state:FormState,itemId: String): Form? {
    return state.forms.find { it.id.toString() == itemId }
}
@ExperimentalMaterial3Api
@Composable
fun DetailFormData(navController:NavHostController, id:String, state: FormState, onEvent:(FormEvent) -> Unit) {

    val form = getItemById(state,id)

    if(form != null){
        val context = LocalContext.current
        LaunchedEffect(emptyList<String>()) {
            onEvent(FormEvent.NameChanged(form.name))
            onEvent(FormEvent.TypeChanged(form.type))
            onEvent(FormEvent.QuantityChanged(form.quantity))
            onEvent(FormEvent.MinPriceChanged(form.minPrice))
            onEvent(FormEvent.MaxPriceChanged(form.maxPrice))
            onEvent(FormEvent.ImageUriChanged(Uri.parse(form.imageURI)))
        }
        LaunchedEffect(state.searchText){
            if(state.searchText.isNotEmpty()||state.searchText.isNotBlank()){
                Toast.makeText(
                    context,
                    state.searchText,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        val modifier = Modifier
        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()){
            uri: Uri?->uri?.let{
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                onEvent(FormEvent.ImageUriChanged(it))
            }
        }

        Column(modifier= modifier
            .fillMaxSize()
            .padding(16.dp)
        ){
            CenterAlignedTopAppBar(
                title = { Text("Detail Data Barang", overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold) },
                modifier = modifier
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.name,
                onValueChange = {
                    onEvent(FormEvent.NameChanged(it))
                },

                label = { androidx.compose.material.Text(text= stringResource(id = R.string.nama_barang)) },
                placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.contoh_nama_barang)) },
                singleLine = true,
                isError = state.nameError != null,
                modifier = modifier.fillMaxWidth(),
            )
            if(state.nameError != null){
                androidx.compose.material.Text(
                    text = state.nameError,
                    color = MaterialTheme.colors.error
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = state.type,
                onValueChange = {onEvent(FormEvent.TypeChanged(it))  },
                label = { androidx.compose.material.Text(text = stringResource(id = R.string.tipe_barang)) },
                placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.contoh_tipe_barang)) },
                singleLine = true,
                isError = state.typeError != null,
                modifier = modifier.fillMaxWidth(),
            )
            if(state.typeError != null){
                androidx.compose.material.Text(
                    text = state.typeError,
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = state.quantity,
                onValueChange = {onEvent(FormEvent.QuantityChanged(it))},
                label = { androidx.compose.material.Text(text = stringResource(id = R.string.jumlah_barang)) },
                placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.contoh_jumlah_barang)) },
                singleLine = true,
                isError = state.quantityError != null,
                modifier = modifier.fillMaxWidth(),
            )
            if(state.quantityError != null){
                androidx.compose.material.Text(
                    text = state.quantityError,
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = state.minPrice,
                onValueChange = {onEvent(FormEvent.MinPriceChanged(it))},
                label = { androidx.compose.material.Text(text = stringResource(id = R.string.harga_batas_bawah)) },
                placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.contoh_harga_batas_bawah)) },
                singleLine = true,
                isError = state.minPriceError != null,
                modifier = modifier.fillMaxWidth(),
            )
            if(state.minPriceError != null){
                androidx.compose.material.Text(
                    text = state.minPriceError,
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            TextField(
                value = state.maxPrice,
                onValueChange = {onEvent(FormEvent.MaxPriceChanged(it))},
                label = { androidx.compose.material.Text(text = stringResource(id = R.string.harga_batas_atas)) },
                placeholder = { androidx.compose.material.Text(text = stringResource(id = R.string.contoh_harga_batas_atas)) },
                singleLine = true,
                isError = state.maxPriceError != null,
                modifier = modifier.fillMaxWidth(),
            )
            if(state.maxPriceError != null){
                androidx.compose.material.Text(
                    text = state.maxPriceError,
                    color = MaterialTheme.colors.error
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            TextField(value=stringResource(id=R.string.klik_button_dibawah),onValueChange = {},label = {
                androidx.compose.material.Text(
                    text = stringResource(id = R.string.foto_barang)
                )
            },
                readOnly=true,modifier = modifier.fillMaxWidth(), isError = state.imageUriError!=null)
            if(state.imageUriError != null){
                androidx.compose.material.Text(
                    text = state.imageUriError,
                    color = MaterialTheme.colors.error
                )
                Spacer(modifier = Modifier.height(5.dp))
            }else{ Spacer(modifier = Modifier.height(5.dp))}
            Row(modifier= modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween){
                androidx.compose.material.OutlinedButton(
                    onClick = {
                        launcher.launch(arrayOf("image/*"))
                    },
                    modifier = modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                        .border(
                            BorderStroke(2.dp, Color.LightGray),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    if (state.imageUri != null) {

                        Image(
                            painter = rememberImagePainter(data = state.imageUri),
                            contentDescription = state.imageUri.toString(),
                            modifier = modifier.width(100.dp),
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = null,
                            alignment = Alignment.Center,
                            modifier = modifier.width(100.dp),
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                }
                Column(modifier = modifier
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    .width(200.dp)){
                    androidx.compose.material.Button(
                        onClick = {
                            onEvent(FormEvent.DeleteData(form))
                            onEvent(FormEvent.Clear)
                            navController.navigateUp()
                        }, modifier = modifier
                            .width(200.dp)
                            .padding(0.dp, 0.dp, 10.dp, 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(id = R.string.delete_data),
                            textAlign = TextAlign.Center
                        )
                    }
                    androidx.compose.material.Button(
                        onClick = {
                            onEvent(FormEvent.EditData(form.id))
                            onEvent(FormEvent.Clear)
                            navController.navigateUp()
                        }, modifier = modifier.width(200.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(id = R.string.ubah_data),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}