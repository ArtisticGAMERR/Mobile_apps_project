package com.example.createacc

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private var isFragmentOneVisible = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, FragmentOne())
                .commit()

            val toggleButton = findViewById<Button>(R.id.button_toggle_fragment)
            toggleButton.setOnClickListener {
                if (isFragmentOneVisible) {
                    switchFragment(FragmentTwo())
                } else {
                    switchFragment(FragmentOne())
                }
                isFragmentOneVisible = !isFragmentOneVisible
            }
        }

        private fun switchFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .addToBackStack(null)
                .commit()
        }


}