package com.example.tokoku.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tokoku.R
import com.example.tokoku.model.FormEvent
import com.example.tokoku.model.FormState

@Composable
fun AddCustomersDialog(state:FormState,onEvent:(FormEvent)->Unit,modifier: Modifier=Modifier) {
    AlertDialog(modifier = modifier, onDismissRequest = { onEvent(FormEvent.HideDialog) },
        title = { Text(modifier=modifier.fillMaxWidth(),text = "Tambah Pelanggan Kasbon", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)},
        text = {
           Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
               TextField(
                value = state.customerName,
                onValueChange = { onEvent(FormEvent.CustomerNameChanged(it)) },
                label = { Text(text = stringResource(id = R.string.nama_pelanggan_kasbon)) },
                placeholder = { Text(text = stringResource(id = R.string.contoh_nama_pelanggan)) },
                singleLine = true,
                isError = state.customerNameError != null,
                modifier = modifier.fillMaxWidth(),
                )
                if(state.customerNameError != null){
                    Text(
                        text = state.customerNameError,
                        color = MaterialTheme.colors.error
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
               TextField(
                   value = state.paymentStatus,
                   onValueChange = {onEvent(FormEvent.PaymentStatusChanged(it))  },
                   label = {Text(text= stringResource(id = R.string.status_pembayaran_pelanggan_kasbon))},
                   placeholder = {Text(text = stringResource(id = R.string.contoh_payment_status))},
                   singleLine = true,
                   isError = state.paymentStatusError != null,
                   modifier = modifier.fillMaxWidth(),
               )
               if(state.paymentStatusError != null){
                   Text(
                       text = state.paymentStatusError,
                       color = MaterialTheme.colors.error
                   )
               }
               Spacer(modifier = Modifier.height(2.dp))
               TextField(
                   value = state.nominalPayment,
                   onValueChange = {onEvent(FormEvent.NominalPaymentChanged(it))},
                   label = {Text(text=stringResource(id=R.string.nominal_payment_pelanggan_kasbon))},
                   placeholder = {Text(text = stringResource(id=R.string.contoh_nominal_payment))},
                   singleLine = true,
                   isError = state.nominalPaymentError != null,
                   modifier = modifier.fillMaxWidth(),
               )
               if(state.nominalPaymentError != null){
                   Text(
                       text = state.nominalPaymentError,
                       color = MaterialTheme.colors.error
                   )
               }
               Spacer(modifier = Modifier.height(2.dp))
               TextField(
                   value = state.paymentDate,
                   onValueChange = {onEvent(FormEvent.PaymentDateChanged(it))},
                   label = {Text(text=stringResource(id=R.string.payment_date_pelanggan_kasbon))},
                   placeholder = {Text(text = stringResource(id=R.string.contoh_payment_date))},
                   singleLine = true,
                   isError = state.paymentDateError != null,
                   modifier = modifier.fillMaxWidth(),
               )
               if(state.paymentDateError != null){
                   Text(
                       text = state.paymentDateError,
                       color = MaterialTheme.colors.error
                   )
               }
           }
        },
        buttons = {
            Row(modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceAround ){
                Box(modifier){
                    Button(modifier=modifier,onClick = {
                        onEvent(FormEvent.HideDialog)
                    }, colors = ButtonDefaults.buttonColors(Color.LightGray)) {
                        Text(text = "Batal")
                    }
                }
                Box(modifier){
                    Button(onClick = {
                        onEvent(FormEvent.SubmitCustomerData)
                    }) {
                        Text(text = "Simpan Data")
                    }
                }
            }
        }
    )
}