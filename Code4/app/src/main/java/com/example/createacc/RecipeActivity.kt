package com.example.createacc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val items = listOf(
            Item(1, "Apple_Pie", R.drawable.apple_pie),
            Item(2, "Chocolate", R.drawable.chocolate),
            Item(3, "Fruit", R.drawable.fruit),
            Item(4, "Sandwich", R.drawable.sandwich),
            Item(5, "Pancakes", R.drawable.pancakes),
            Item(5, "Salad", R.drawable.salad),
        )

        val adapter = ItemAdapter(
            itemList = items,
            onItemClicked = { item ->
                Toast.makeText(this, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
            },
            onButtonClicked = { item, action ->
                Toast.makeText(this, "$action on ${item.name}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
