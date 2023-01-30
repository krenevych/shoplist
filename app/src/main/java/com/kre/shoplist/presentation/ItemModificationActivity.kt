package com.kre.shoplist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item
import kotlinx.android.synthetic.main.activity_item_modification.*

class ItemModificationActivity : AppCompatActivity() {

    private val TAG: String = "MMMMMMMMM"
    private lateinit var viewModel: ItemModificationViewModel

    private lateinit var mode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_modification)

        createVM()

        mode = intent.getStringExtra(Constants.EXTRA_MODE) ?: Constants.MODE_ADD
        Log.e(TAG, "onCreate: mode = $mode")

        val itemId = intent.getIntExtra(Constants.EXTRA_ITEM_ID_NAME, Item.UNDEFINED_ID)
        itemId.let {
            viewModel.getItem(it)
            Log.e(TAG, "onCreate: ")
        }

        when (mode) {
            Constants.MODE_EDIT -> launchInEditMode()
            Constants.MODE_ADD -> launchInAddMode()
        }

        initEditViews()
    }

    private fun launchInEditMode() {
        Log.d(TAG, "launchInEditMode: ")
        button_save.setOnClickListener {
            viewModel.editItem(
                edit_text_name.text.toString(),
                edit_text_count.text.toString()
            )
        }
    }

    private fun launchInAddMode() {
        Log.d(TAG, "launchInAddMode: ")
        button_save.setOnClickListener {
            viewModel.addItem(
                edit_text_name.text.toString(),
                edit_text_count.text.toString()
            )
        }
    }

    private fun createVM() {
        viewModel = ViewModelProvider(this)[ItemModificationViewModel::class.java]

        viewModel.errorNameLiveData.observe(this) {
            til_name.error = if (it) "Please enter valid name" else null
        }

        viewModel.errorCountLiveData.observe(this) {
            til_count.error = if (it) "Number has be grater than zero" else null
        }

        viewModel.closeEvent.observe(this) {
            if (it) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.item.observe(this) {
            it?.let {
                Log.e(TAG, "createVM: viewModel.item.observe")

                edit_text_name.setText(it.name)
                edit_text_count.setText(it.count.toString())
            }
        }
    }

    private fun initEditViews() {
        edit_text_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetNameLiveData()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        edit_text_count.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetCountLiveData()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }
}
