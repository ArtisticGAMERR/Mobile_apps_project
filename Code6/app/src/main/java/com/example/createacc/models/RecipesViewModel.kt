package com.example.createacc.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.createacc.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class RecipesViewModel : ViewModel() {
    private val _allItems = mutableListOf<Item>()
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    private val _query = MutableStateFlow("")

    init {
        _query
            .debounce(300)
            .onEach { query ->
                if (query.isNotEmpty()) {
                    setLoadingState()
                    delay(2000)
                    applyQueryFilter(query)
                } else {
                    resetItems()
                }
            }
            .launchIn(viewModelScope)
    }

    fun setInitialItems(items: List<Item>) {
        _allItems.clear()
        _allItems.addAll(items)
        _uiState.value = UiState(recipes = _allItems)
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    private fun setLoadingState() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun resetItems() {
        _uiState.update { it.copy(isLoading = false, recipes = _allItems, errorMessage = null) }
    }

    private fun applyQueryFilter(query: String) {
        val filtered = if (query.length < 3) {
            _allItems
        } else {
            _allItems.filter { item ->
                item.name.lowercase().contains(query.lowercase())
            }
        }

        _uiState.update {
            it.copy(isLoading = false, recipes = filtered, errorMessage = null)
        }
    }
}
