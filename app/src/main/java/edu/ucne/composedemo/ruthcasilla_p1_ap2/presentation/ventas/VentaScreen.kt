package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.composedemo.ruthcasilla_p1_ap2.ui.theme.blueCustom


@Composable
fun VentaScreen(
    viewModel: VentaViewModel = hiltViewModel(),
    onDrawerToggle: () -> Unit,
    goToVenta: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VentaScreenBody(
        uiState = uiState,
        onNombreClienteChange = viewModel::onNombreClienteChange,
        onGalonesChange = viewModel::onGalonesChange,
        onDescuentoGalonChange = viewModel::onDescuentoGalonChange,
        onPrecioChange = viewModel::onPrecioChange,
        onTotalDescontadoChange = viewModel::onTotalDescontadoChange,
        onTotalChange = viewModel::onTotalChange,
        onDrawerToggle = onDrawerToggle,
        goToVenta = goToVenta,
        saveVenta  = viewModel::save
    )
}

@Composable
fun VentaScreenBody(
    uiState: UiState,
    onDrawerToggle: () -> Unit,
    onNombreClienteChange: (String) -> Unit,
    onGalonesChange: (Double) -> Unit,
    onDescuentoGalonChange: (Double) -> Unit,
    onPrecioChange: (Double) -> Unit,
    onTotalDescontadoChange: (Double) -> Unit,
    onTotalChange: (Double) -> Unit,
    goToVenta: () -> Unit,
    saveVenta: () -> Unit
) {
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
                containerColor = White
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

                // Campo para el Nombre
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(BorderStroke(2.dp, blueCustom), shape = RoundedCornerShape(10.dp))
                ) {
                    BasicTextField(
                        value = uiState.nombreCliente ?: "",
                        onValueChange = onNombreClienteChange,
                        textStyle = TextStyle(
                            color = Black,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 22.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp)
                    ) {
                        this@Column.AnimatedVisibility(
                            visible = uiState.nombreCliente.isNullOrEmpty(),
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier.offset(y = 14.dp)
                        ) {
                            Text(
                                text = "Nombre",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                //Campo de Galones

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, blueCustom), shape = RoundedCornerShape(10.dp))
                ) {
                    BasicTextField(
                        value = uiState.galones?.toString() ?: "",
                        onValueChange = { newValue ->
                            val galonDouble = newValue.toDoubleOrNull()
                            if (galonDouble != null) {
                                onGalonesChange(galonDouble)
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Black,
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 22.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                innerTextField()
                                if (uiState.galones == null || uiState.galones == 0.0) {
                                    Text(
                                        text = "Galones",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 8.dp)
                                            .align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                    )
                }

                //Campo de Descuento Galon

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, blueCustom), shape = RoundedCornerShape(10.dp))
                ) {
                    BasicTextField(
                        value = uiState.descuentoGalon?.toString() ?: "",
                        onValueChange = { newValue ->
                            val descuentoGalonDouble = newValue.toDoubleOrNull()
                            if (descuentoGalonDouble != null) {
                                onDescuentoGalonChange(descuentoGalonDouble)
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Black,
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 22.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                innerTextField()
                                if (uiState.descuentoGalon == null || uiState.descuentoGalon == 0.0) {
                                    Text(
                                        text = "Descuento de Galones",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 8.dp)
                                            .align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                    )
                }

                //Campo de Precio

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, blueCustom), shape = RoundedCornerShape(10.dp))
                ) {
                    BasicTextField(
                        value = uiState.precio?.toString() ?: "",
                        onValueChange = { newValue ->
                            val precioDouble = newValue.toDoubleOrNull()
                            if (precioDouble != null) {
                                onPrecioChange(precioDouble)
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Black,
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 22.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                innerTextField()
                                if (uiState.precio == null || uiState.precio == 0.0) {
                                    Text(
                                        text = "Precio",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 8.dp)
                                            .align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                    )
                }

                //Campo de Total Descontado

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, blueCustom), shape = RoundedCornerShape(10.dp))
                ) {
                    BasicTextField(
                        value = uiState.totalDescontado?.toString() ?: "",
                        onValueChange = { newValue ->
                            val totalDescontadoDouble = newValue.toDoubleOrNull()
                            if (totalDescontadoDouble != null) {
                                onTotalDescontadoChange(totalDescontadoDouble)
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Black,
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 22.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                innerTextField()
                                if (uiState.totalDescontado == null || uiState.totalDescontado == 0.0) {
                                    Text(
                                        text = "Total Descontado",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 8.dp)
                                            .align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                    )
                }

                //Campo de Galones

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, blueCustom), shape = RoundedCornerShape(10.dp))
                ) {
                    BasicTextField(
                        value = uiState.total?.toString() ?: "",
                        onValueChange = { newValue ->
                            val totalDouble = newValue.toDoubleOrNull()
                            if (totalDouble != null) {
                                onTotalChange(totalDouble)
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Black,
                            fontSize = 16.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 22.dp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                innerTextField()
                                if (uiState.total == null || uiState.total == 0.0) {
                                    Text(
                                        text = "Total",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 8.dp)
                                            .align(Alignment.TopStart)
                                    )
                                }
                            }
                        }
                    )
                }


                // Mostrar error si existe
                uiState.errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Botón para guardar
                Button(
                    onClick = {
                        saveVenta()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueCustom
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Guardar",
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Guardar",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
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
fun MetaScreenPreview() {

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
    val onNombreClienteChange: (String) -> Unit = {}
    val onGalonesChange: (Double) -> Unit = {}
    val onDescuentoGalonChange: (Double) -> Unit = {}
    val onPrecioChange: (Double) -> Unit = {}
    val onTotalDescontadoChange: (Double) -> Unit = {}
    val onTotalChange: (Double) -> Unit = {}
    val goToVenta: () -> Unit = {}
    val saveVenta: () -> Unit = {}

    VentaScreenBody(
        uiState = testUiState,
        onDrawerToggle = onDrawerToggle,
        onNombreClienteChange = onNombreClienteChange,
        onGalonesChange = onGalonesChange,
        onDescuentoGalonChange = onDescuentoGalonChange,
        onPrecioChange = onPrecioChange,
        onTotalDescontadoChange = onTotalDescontadoChange,
        onTotalChange = onTotalChange,
        goToVenta  = goToVenta,
        saveVenta = saveVenta
    )
}


