package com.example.approvalmatrix.ui

import androidx.lifecycle.ViewModel
import com.example.approvalmatrix.data.UiState
import com.example.approvalmatrix.model.ApprovalMatrix
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun addToList(item: ApprovalMatrix) {
        _uiState.update { currentState ->
            currentState.apply {
                approvalMatrixList.add(item)
            }
        }
    }

    fun setIndex(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                index = index
            )
        }
    }

    fun setItem(item: ApprovalMatrix) {
        _uiState.update { currentState ->
            currentState.copy(
                item = item
            )
        }
    }

    fun updateList(index: Int) {
        _uiState.update { currentState ->
            currentState.apply {
                approvalMatrixList[index].alias = item.alias
                approvalMatrixList[index].feature = item.feature
                approvalMatrixList[index].min = item.min
                approvalMatrixList[index].max = item.max
                approvalMatrixList[index].num = item.num
            }
        }
    }

    fun deleteList(index: Int) {
        _uiState.update { currentState ->
            currentState.apply {
                approvalMatrixList.removeAt(index)
            }
        }
    }
}