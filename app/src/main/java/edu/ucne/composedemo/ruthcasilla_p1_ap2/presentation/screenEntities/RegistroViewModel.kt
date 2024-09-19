package edu.ucne.composedemo.ruthcasilla_p1_ap2.presentation.screenEntities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.entities.AlgoEntity
import edu.ucne.composedemo.ruthcasilla_p1_ap2.data.local.repository.AlgoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val algoRepository: AlgoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {

    }


    fun delete() {
        viewModelScope.launch {
            algoRepository.delete(_uiState.value.toEntity())
        }
    }

    private fun getAlgos() {
        viewModelScope.launch {
            algoRepository.getAlgos().collect { algo ->
                _uiState.update {
                    it.copy()
                }
            }
        }
    }
}

data class UiState(
    val algoId: Int? = null,
    val algs: List<AlgoEntity> = emptyList()
)

fun UiState.toEntity() = AlgoEntity(
    algoId = algoId
)