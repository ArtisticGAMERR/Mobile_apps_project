package com.example.createacc

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.createacc.models.RecipesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeActivity : AppCompatActivity() {

    private val viewModel: RecipesViewModel by viewModels()
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val searchView: SearchView = findViewById(R.id.searchView)

        val initialItems = listOf(
            Item(1, "Apple Pie", R.drawable.apple_pie),
            Item(2, "Chocolate", R.drawable.chocolate),
            Item(3, "Fruit Salad", R.drawable.fruit),
            Item(4, "Sandwich", R.drawable.sandwich),
            Item(5, "Pancakes", R.drawable.pancakes),
            Item(6, "Green Salad", R.drawable.salad),
        )

        // Initialize ViewModel with the initial list of items
        viewModel.setInitialItems(initialItems)

        // Set up the RecyclerView and adapter
        adapter = ItemAdapter(
            itemList = emptyList(),
            onItemClicked = { item ->
                Toast.makeText(this, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
            },
            onButtonClicked = { item, action ->
                Toast.makeText(this, "$action on ${item.name}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe filtered items and update the adapter
        lifecycleScope.launch {
            viewModel.filteredItems.collectLatest { filteredItems ->
                adapter.updateItems(filteredItems)
            }
        }

        // Listen for search input changes
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true // Handle search submission if needed
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchItems(newText.orEmpty())
                return true
            }
        })
    }
}
