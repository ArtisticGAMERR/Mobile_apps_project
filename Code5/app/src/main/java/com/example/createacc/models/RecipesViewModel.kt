package com.example.createacc.models

import androidx.lifecycle.ViewModel
import com.example.createacc.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RecipesViewModel : ViewModel() {
    private val _allItems = mutableListOf<Item>()
    private val _filteredItems = MutableStateFlow<List<Item>>(emptyList())
    val filteredItems: StateFlow<List<Item>> = _filteredItems

    fun setInitialItems(items: List<Item>) {
        _allItems.clear()
        _allItems.addAll(items)
        _filteredItems.value = _allItems
    }

    fun searchItems(query: String) {
        if (query.length < 3) {
            _filteredItems.update { _allItems }
        } else {
            val lowerCaseQuery = query.lowercase()
            _filteredItems.update {
                _allItems.filter { item ->
                    item.name.lowercase().contains(lowerCaseQuery)
                }
            }
        }
    }
}
