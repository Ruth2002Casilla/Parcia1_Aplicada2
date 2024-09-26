package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.ventas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.ventasEntity
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.repository.VentaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentaViewModel @Inject constructor(
    private val ventaRepository: VentaRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getVentas()
    }

    private fun validar(): Boolean {
        if (_uiState.value.nombreCliente.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorMessage = "El Nombre del Cliente no puede estar vacio")
            }
            return false
        }
        if (_uiState.value.galones == null || _uiState.value.galones == 0.0) {
            _uiState.update {
                it.copy(errorMessage = "Los Galones  no pueden  estar vacío o ser 0")
            }
            return false
        }
        if (_uiState.value.descuentoGalon == null || _uiState.value.descuentoGalon == 0.0) {
            _uiState.update {
                it.copy(errorMessage = "El Descuento por Galon  no puede  estar vacío o ser 0")
            }
            return false
        }

        if (_uiState.value.precio == null || _uiState.value.precio == 0.0) {
            _uiState.update {
                it.copy(errorMessage = "El Precio  no puede  estar vacío o ser 0")
            }
            return false
        }
        if (_uiState.value.totalDescontado == null || _uiState.value.totalDescontado == 0.0) {
            _uiState.update {
                it.copy(errorMessage = "El Total Descontado  no puede  estar vacío o ser 0")
            }
            return false
        }
        if (_uiState.value.total == null || _uiState.value.total == 0.0) {
            _uiState.update {
                it.copy(errorMessage = "El Total  no puede  estar vacío o ser 0")
            }
            return false
        }
        return true
    }

    fun save() {
        viewModelScope.launch {
            if (!validar()) return@launch
            val venta = uiState.value.toEntity()
            ventaRepository.save(venta)
            _uiState.update {
                it.copy(errorMessage = null, guardado = true)
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            if (!validar()) return@launch

            val venta = uiState.value.toEntity()
            ventaRepository.save(venta)
            _uiState.update {
                it.copy(errorMessage = null, guardado = true)
            }
        }
    }

    fun selectedVenta(ventaId: Int) {
        viewModelScope.launch {
            if (ventaId > 0) {
                val venta = ventaRepository.getVenta(ventaId)
                _uiState.update { currentState ->
                    currentState.copy(
                        ventaId = venta?.ventaId,
                        nombreCliente = venta?.nombreCliente,
                        galones  = venta?.galones,
                        descuentoGalon = venta?.descuentoGalon,
                        precio = venta?.precio,
                        totalDescontado = venta?.totalDescontado,
                        total = venta?.total,
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            if (uiState.value.ventaId != null) {
                ventaRepository.delete(_uiState.value.toEntity())
                _uiState.update { it.copy(ventaId = null,  guardado = true) }
            }
        }
    }

    private fun getVentas() {
        viewModelScope.launch {
            ventaRepository.getAll().collect { ventas ->
                _uiState.update { it.copy(ventas = ventas) }
            }
        }
    }

    fun onNombreClienteChange(nombreCliente: String) {
        _uiState.update {
            it.copy(nombreCliente = nombreCliente)
        }
    }

    fun onGalonesChange(galones: Double?) {
        _uiState.update {
            it.copy(galones = galones)
        }
    }

    fun onDescuentoGalonChange(descuentoGalon: Double?) {
        _uiState.update {
            it.copy(descuentoGalon = descuentoGalon)
        }
    }

    fun onPrecioChange(precio: Double?) {
        _uiState.update {
            it.copy(precio = precio)
        }
        calcularTotales()
    }

    fun onTotalDescontadoChange(totalDescontado: Double?) {
        _uiState.update {
            it.copy(totalDescontado = totalDescontado)
        }
        calcularTotales()
    }

    fun onTotalChange(total: Double?) {
        _uiState.update {
            it.copy(total = total)
        }
        calcularTotales()
    }

    fun calcularTotales() {
        val galones = _uiState.value.galones ?: 0.0
        val descuentoGalon = _uiState.value.descuentoGalon ?: 0.0
        val precio = _uiState.value.precio ?: 0.0

        val totalDescontado = galones * descuentoGalon
        val total = (galones * precio) - totalDescontado

        _uiState.update {
            it.copy(
                totalDescontado = totalDescontado,
                total = total
            )
        }
    }

}

data class UiState(
    val ventaId: Int? = null,
    val nombreCliente: String? = "",
    val galones: Double? = null,
    val descuentoGalon: Double? = null,
    val precio: Double? = null,
    val totalDescontado: Double? = null,
    val total: Double? = null,
    val ventas: List<ventasEntity> = emptyList(),
    val errorMessage: String? = null,
    var guardado: Boolean? = false,
)

fun UiState.toEntity() = ventasEntity(
    ventaId = ventaId,
    nombreCliente = nombreCliente,
    galones = galones,
    descuentoGalon = descuentoGalon,
    precio = precio,
    totalDescontado = totalDescontado,
    total = total
)