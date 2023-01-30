package com.kre.shoplist.presentation

import android.os.Bundle
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
            Log.e(TAG, "onCreate: ", )
        }

        initSaveBtn()
    }

    private fun initSaveBtn() {
        Log.e(TAG, "initSaveBtn: ")
        button_save.setOnClickListener {
            if (mode == Constants.MODE_EDIT) {
                viewModel.editItem(
                    edit_text_name.text.toString(),
                    edit_text_count.text.toString()
                )
            } else {
                viewModel.addItem(
                    edit_text_name.text.toString(),
                    edit_text_count.text.toString()
                )
            }
        }
    }

    private fun createVM() {
        viewModel = ViewModelProvider(this)[ItemModificationViewModel::class.java]

        viewModel.errorNameLiveData.observe(this) {
            if (it) {
                Toast.makeText(this, "NameError", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorCountLiveData.observe(this) {
            if (it) {
                Toast.makeText(this, "CountError", Toast.LENGTH_SHORT).show()
            }
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
}
