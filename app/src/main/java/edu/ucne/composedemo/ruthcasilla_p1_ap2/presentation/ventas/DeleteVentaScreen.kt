package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.ruthcasilla_p1_ap2.ui.theme.blueCustom

@Composable
fun DeleteVentaScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    ventaId: Int?,
    onDrawerToggle: () -> Unit,
    goToVenta: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        if (ventaId != null) {
            viewModel.selectedVenta(ventaId)
        }
    }
    EliminarVentaScreenBody(
        uiState = uiState,
        onDrawerToggle = onDrawerToggle,
        goToVenta = goToVenta,
        deleteVenta = {
            viewModel.delete()
        }
    )
}

@Composable
fun EliminarVentaScreenBody(
    uiState: UiState,
    onDrawerToggle: () -> Unit,
    goToVenta: () -> Unit,
    deleteVenta: () -> Unit,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(3.dp, blueCustom, shape = RoundedCornerShape(30.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¿Estás seguro que deseas eliminar esta VENTA?",
                    style = TextStyle(
                        color = blueCustom,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                uiState.errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            deleteVenta()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = blueCustom
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Confirmar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            goToVenta()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Red
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
        LaunchedEffect(uiState.guardado) {
            if (uiState.guardado == true) {
                goToVenta()
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EliminarMetaScreenPreview() {

    val testUiState = UiState(
        nombreCliente = "",
        galones = null,
        descuentoGalon = null,
        precio = null,
        totalDescontado = null,
        total = null,
        errorMessage = null,
        guardado = false
    )

    val onDrawerToggle: () -> Unit = {}
    val goToVenta: () -> Unit = {}
    val deleteVenta: () -> Unit = {}

    EliminarVentaScreenBody(
        uiState = testUiState,
        onDrawerToggle = onDrawerToggle,
        goToVenta = goToVenta,
        deleteVenta = deleteVenta
    )
}
