package com.example.storeapp.presentation

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
import com.example.storeapp.R
import com.example.storeapp.domain.model.ShopItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(

) : Fragment() {
    private lateinit var viewModel: ShopItemViewModel
    lateinit var tilName: TextInputLayout
    lateinit var tilCount: TextInputLayout
    lateinit var inputEditTextName: TextInputEditText
    lateinit var inputEditTextCount: TextInputEditText
    private lateinit var button: Button
    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("checkFun", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()

        Log.d("checkFun", "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d("checkFun", "onCreateView: ")
        return layoutInflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("checkFun", "onViewCreated: ")

        viewModel = ViewModelProvider(this@ShopItemFragment)[ShopItemViewModel::class.java]
        initViews(view)
        addChangeTextListeners()
        launchRightMode()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        Log.d("checkFun", "onStart(): ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("checkFun", "onStop(): ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("checkFun", "onPause(): ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("checkFun", "onDestroy(): ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("checkFun", "onDetach(): ")
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

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
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
            activity?.onBackPressed()
        }
    }

    private fun launchAddMode() {
        button.setOnClickListener {
            val name = inputEditTextName.text.toString()
            val count = inputEditTextCount.text.toString()
            viewModel.addShopItem(name, count)
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
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

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param of screen mode is absent")
        }

        screenMode = args.getString(SCREEN_MODE).toString()
        if (screenMode != MODE_EDIT && screenMode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $screenMode")
        }

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("EXTRA_SHOP_ITEM_ID Param doesn't has ID")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {

        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        inputEditTextName = view.findViewById(R.id.et_name)
        inputEditTextCount = view.findViewById(R.id.et_count)
        button = view.findViewById(R.id.button_save)
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""


        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}