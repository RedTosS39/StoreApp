package com.example.storeapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.storeapp.R
import com.example.storeapp.domain.model.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {
    private lateinit var viewModel: ShopItemViewModel
    lateinit var tilName: TextInputLayout
    lateinit var tilCount: TextInputLayout
    lateinit var inputEditTextName: TextInputEditText
    lateinit var inputEditTextCount: TextInputEditText
    private lateinit var button: Button
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parseParams()
        viewModel = ViewModelProvider(this@ShopItemFragment)[ShopItemViewModel::class.java]
        initViews(view)
        addChangeTextListeners()
        launchMode()
        observeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeViewModel() {
        viewModel.inputCountError.observe(viewLifecycleOwner) {
            val massage = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = massage
        }
        viewModel.inputNameError.observe(viewLifecycleOwner) {

            val massage = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }

            tilName.error = massage
        }
    }

    private fun launchMode() {
        when (screenMode) {
            MODE_ADD -> {
                launchAddMode()
            }
            MODE_EDIT -> {
                launchEditMode()
            }
        }

    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            inputEditTextName.setText(it.name)
            inputEditTextCount.setText(it.count.toString())
        }

        button.setOnClickListener {
            val name = inputEditTextName.text?.toString()
            val count = inputEditTextCount.text?.toString()
            viewModel.editShopItem(name, count)
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
//            finish()
        }
    }

    private fun launchAddMode() {
        button.setOnClickListener {
            val name = inputEditTextName.text.toString()
            val count = inputEditTextCount.text.toString()
            viewModel.addShopItem(name, count)
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
//            finish()
        }
    }

    private fun addChangeTextListeners() {
        inputEditTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        inputEditTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun initViews(view: View) {
        with(view) {
            tilName = findViewById(R.id.til_name)
            tilCount = findViewById(R.id.til_count)
            inputEditTextName = findViewById(R.id.et_name)
            inputEditTextCount = findViewById(R.id.et_count)
            button = findViewById(R.id.button_save)

        }
    }

    private fun parseParams() {
//        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
//            throw RuntimeException("Param of screen mode is absent")
//        }
//
//        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
//        if (mode != MODE_EDIT && mode != MODE_ADD) {
//            throw RuntimeException("Unknown screen mode $mode")
//        }
//
//        screenMode = mode
//        if (screenMode == MODE_EDIT) {
//            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
//                throw RuntimeException("EXTRA_SHOP_ITEM_ID Param doesn't has ID")
//            } else {
//                shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
//            }
//        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

}