package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.composedemo.ruthcasilla_p1_ap2.R
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.ventasEntity
import edu.ucne.composedemo.ruthcasilla_p1_ap2.ui.theme.blueCustom

@Composable
fun ListVentasScreen(
    onDrawerToggle: () -> Unit,
    goToVenta: () -> Unit,
    createVenta: () -> Unit,
    editVenta: (Int) -> Unit,
    deleteVenta: (Int) -> Unit,
    viewModel: VentaViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { createVenta() },
                containerColor = White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Crear nueva Venta"
                )
            }
        }

    ) { contentPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            Button(
                onClick = onDrawerToggle,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 50.dp, start = 5.dp)
                    .background(Color.Transparent),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_menu_open_24),
                        contentDescription = "Menu Icon",
                        tint = Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ventas",
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 280.dp)
                ) {
                    if (uiState.ventas.isEmpty()) {
                        MensajePersonalizado()
                    } else {
                        VentasList(
                            ventas = uiState.ventas,
                            onEditClick = { venta ->
                                venta.ventaId?.let { id ->
                                    editVenta(id)
                                }
                            },
                            onDeleteClick = { venta ->
                                venta.ventaId?.let { id ->
                                    deleteVenta(id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun VentasList(
    ventas: List<ventasEntity>,
    onEditClick: (ventasEntity) -> Unit,
    onDeleteClick: (ventasEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp)
    ) {
        items(ventas) { venta: ventasEntity ->
            VentasCard(
                venta = venta,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                index = ventas.indexOf(venta) + 1
            )
        }
    }
}

@Composable
fun VentasCard(
    venta: ventasEntity,
    onEditClick: (ventasEntity) -> Unit,
    onDeleteClick: (ventasEntity) -> Unit,
    index: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 4.dp
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = blueCustom)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "$index.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = venta.nombreCliente?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = White
                )
                Text(
                    text = "${venta.total} pesos",
                    fontSize = 14.sp,
                    color = White
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = { onEditClick(venta) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_edit_24),
                        contentDescription = "Editar",
                        modifier = Modifier.size(32.dp)
                    )
                }
                IconButton(onClick = { onDeleteClick(venta) }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MensajePersonalizado() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "NO SE ENCONTRARON VENTAS",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "NO SE ENCONTRARON VENTAS",
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListScreenPreview() {
    ListVentasScreen(
        onDrawerToggle = { /* */ },
        goToVenta = { /* */},
        createVenta = {/* */},
        editVenta = { /* */},
        deleteVenta = { /* */},
    )
}


