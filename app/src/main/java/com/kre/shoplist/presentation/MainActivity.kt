package com.kre.shoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kre.shoplist.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var count  = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this) {
            Log.d("XXWWWX", it.toString())
            if (count > 0){
                viewModel.toggleEnabled(viewModel.getItem(2))
                count = -1
            }
        }

    }
}