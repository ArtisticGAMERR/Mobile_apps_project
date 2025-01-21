package com.example.createacc.models

import com.example.createacc.Item

data class UiState(
    val isLoading: Boolean = false,
    val recipes: List<Item> = emptyList(),
    val errorMessage: String? = null
)
