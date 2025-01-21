package com.example.createacc

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.createacc.models.MyApplication
import com.example.createacc.models.RecipesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeActivity : AppCompatActivity() {

    private val viewModel: RecipesViewModel by viewModels()
    private lateinit var adapter: ItemAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        recyclerView = findViewById(R.id.recyclerView)
        val searchView: SearchView = findViewById(R.id.searchView)
        progressBar = findViewById(R.id.progressBar)
        logoutButton = findViewById(R.id.logoutButton)

        val initialItems = listOf(
            Item(1, "Apple Pie", R.drawable.apple_pie),
            Item(2, "Chocolate", R.drawable.chocolate),
            Item(3, "Fruit Salad", R.drawable.fruit),
            Item(4, "Sandwich", R.drawable.sandwich),
            Item(5, "Pancakes", R.drawable.pancakes),
            Item(6, "Green Salad", R.drawable.salad),
        )

        viewModel.setInitialItems(initialItems)

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

   /*     lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                when (uiState.isLoading) {
                    true -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    false -> {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        adapter.updateItems(uiState.recipes)
                    }
                }
            }
        }*/
        lifecycleScope.launch {
            val credentialsManager = (application as MyApplication).credentialsManager
            credentialsManager.isLoggedIn.collectLatest { isLoggedIn ->
                if (!isLoggedIn) {
                    //navigateToLogin()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                if (uiState.isLoading) {
                } else {
                    adapter.updateItems(uiState.recipes)
                }
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateQuery(newText.orEmpty())
                return true
            }
        })

        logoutButton.setOnClickListener {
            val credentialsManager = (application as MyApplication).credentialsManager
            credentialsManager.logout()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, LoginFragment())
            .commit()
    }
}
