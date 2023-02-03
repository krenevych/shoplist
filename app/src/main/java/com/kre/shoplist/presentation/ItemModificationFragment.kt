package com.kre.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kre.shoplist.R
import com.kre.shoplist.domain.Item


class ItemModificationFragment : Fragment() {

    private val TAG: String = "FFFFFFFF"

    private lateinit var viewModel: ItemModificationViewModel

    private lateinit var buttonSave: Button
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextCount: TextInputEditText
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout

    private var mode: String = Constants.MODE_UNKNOWN
    private var itemId: Int = Item.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseParams(requireArguments())
    }

    interface FinishListener {
        fun onFinish(mode: String)
    }

    private lateinit var finishListener: FinishListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FinishListener){
            finishListener = context
        } else {
            throw RuntimeException("Activity has to implement FinishListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_item_modification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createVM()
        findViews(view)
        viewModel.getItem(itemId)

        launchInRightMode()

        initEditViews()
    }

    private fun parseParams(arguments: Bundle) {
        if (!arguments.containsKey(Constants.MODE)) {
            throw java.lang.RuntimeException("Arguments of fragment don't contain a key Constants.MODE")
        }
        mode = arguments.getString(Constants.MODE) ?: Constants.MODE_UNKNOWN
        if (mode == Constants.MODE_EDIT) {
            if (!arguments.containsKey(Constants.ITEM_ID)) {
                throw java.lang.RuntimeException("Unknown itemId for creating")
            } else {
                itemId = arguments.getInt(Constants.ITEM_ID)
            }
        }
    }

    private fun findViews(view: View) {
        buttonSave = view.findViewById(R.id.button_save)
        editTextName = view.findViewById(R.id.edit_text_name)
        editTextCount = view.findViewById(R.id.edit_text_count)
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
    }

    private fun launchInRightMode() {
        when (mode) {
            Constants.MODE_EDIT -> launchInEditMode()
            Constants.MODE_ADD -> launchInAddMode()
        }
    }

    private fun launchInEditMode() {
        Log.d(TAG, "launchInEditMode: ")
        buttonSave.setOnClickListener {
            viewModel.editItem(
                editTextName.text.toString(),
                editTextCount.text.toString()
            )
        }
    }

    private fun launchInAddMode() {
        Log.d(TAG, "launchInAddMode: ")
        buttonSave.setOnClickListener {
            viewModel.addItem(
                editTextName.text.toString(),
                editTextCount.text.toString()
            )
        }
    }

    private fun createVM() {
        viewModel = ViewModelProvider(this)[ItemModificationViewModel::class.java]

        viewModel.errorNameLiveData.observe(viewLifecycleOwner) {
            tilName.error = if (it) "Please enter valid name" else null
        }

        viewModel.errorCountLiveData.observe(viewLifecycleOwner) {
            tilCount.error = if (it) "Number has be grater than zero" else null
        }

        viewModel.closeEvent.observe(viewLifecycleOwner) {
            if (it) {
                finishListener.onFinish(Constants.MODE_EDIT)
                activity?.onBackPressed()
            }
        }

        viewModel.item.observe(viewLifecycleOwner) {
            it?.let {
                Log.e(TAG, "createVM: viewModel.item.observe")

                editTextName.setText(it.name)
                editTextCount.setText(it.count.toString())
            }
        }
    }

    private fun initEditViews() {
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetNameLiveData()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        editTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetCountLiveData()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    companion object {

        fun newFragmentEditItem(itemId: Int): Fragment {
            return ItemModificationFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.MODE, Constants.MODE_EDIT)
                    putInt(Constants.ITEM_ID, itemId)
                }
            }
        }

        fun newFragmentAddItem(): Fragment {
            return ItemModificationFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.MODE, Constants.MODE_ADD)
                }
            }
        }
    }
}